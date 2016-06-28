// This product is provided under the terms of EPL (Eclipse Public License)
// version 1.0.
//
// The full license text can be read from: http://www.eclipse.org/org/documents/epl-v10.php

package org.dtangler.core.cycleanalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dtangler.core.analysisresult.Violation;
import org.dtangler.core.dependencies.Dependable;

public class DependencyCycle implements Violation {

	private final List<Dependable> elements;
	private final Set<Dependable> searchSet;
	private final List<String> stringElements;
	private final int hashCode;

	public DependencyCycle(List<Dependable> cycleElements) {
		this.elements = cycleElements;
		searchSet = new HashSet<>(cycleElements);

		stringElements = new ArrayList<>();
		for (Dependable item : cycleElements) {
			stringElements.add(item.getDisplayName());
		}
		hashCode = calculateHashCode();
	}

	public List<Dependable> getElements() {
		return elements;
	}

	public List<String> getStringElements() {
		return stringElements;
	}

	public boolean contains(Dependable dependable) {
		return searchSet.contains(dependable);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DependencyCycle)) {
			return false;
		}
		List<String> otherElements = ((DependencyCycle) obj).stringElements;
		if (stringElements.size() != otherElements.size()) {
			return false;
		}

		// get rid of last element
		otherElements = otherElements.subList(0, otherElements.size() - 1);

		for (int i = 0; i < otherElements.size(); i++) {
			if (otherElements.equals(getRolledList(i))) {
				return true;
			}
		}
		return false;
	}

	private List<String> getRolledList(int startIndex) {
		List<String> result = new ArrayList<>();
		result.addAll(stringElements.subList(startIndex, stringElements.size()));
		result.addAll(stringElements.subList(0, startIndex));
		return result;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	private int calculateHashCode() {
		int result = 0;
		for (int i = 0; i < stringElements.size() - 1; i++) {
			result += stringElements.get(i).hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		return asText();
	}

	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		for (Dependable e : elements) {
			if (sb.length() > 0) {
				sb.append("-->");
			}
			sb.append(e.getDisplayName());
		}
		return "Cycle: " + sb.toString();

	}

	@Override
	public Severity getSeverity() {
		return Severity.error;
	}

	@Override
	public boolean appliesTo(Set<Dependable> dependables) {
		return dependables.containsAll(searchSet);
	}

	@Override
	public Set<Dependable> getMembers() {
		return new HashSet<>(getElements());
	}
}
