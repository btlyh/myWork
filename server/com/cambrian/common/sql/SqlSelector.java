/**
 * 
 */
package com.cambrian.common.sql;

import com.cambrian.common.util.Selector;

public abstract interface SqlSelector extends Selector
{

	public abstract String getSql();
}