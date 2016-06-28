// This product is provided under the terms of EPL (Eclipse Public License) 
// version 1.0.
//
// The full license text can be read from: http://www.eclipse.org/org/documents/epl-v10.php 

package org.dtangler.core.textui;

public class SysoutWriter implements Writer {
	@Override
	public void print(String s) {
		System.out.print(s);
	}

	@Override
	public void println(String s) {
		System.out.println(s);
	}

}
