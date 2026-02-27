package de.gravitex.banking.client.gui.dialog.editor.util;

import java.lang.reflect.Field;

public interface EditorItemListener {

	void onFieldValueChanged(Object aChangedValue, Field aField);
}