package com.kaylerrenslow.armaDialogCreator.control.sv;

import com.kaylerrenslow.armaDialogCreator.control.PropertyType;
import com.kaylerrenslow.armaDialogCreator.util.DataContext;
import com.kaylerrenslow.armaDialogCreator.util.ValueConverter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 Created by Kayler on 07/13/2016.
 */
public final class SVStringArray extends SerializableValue {
	
	public static final ValueConverter<SVStringArray> CONVERTER = new ValueConverter<SVStringArray>() {
		@Override
		public SVStringArray convert(DataContext context, @NotNull String... values) throws Exception {
			return new SVStringArray(values);
		}
	};
	private final String[] strings;

	public SVStringArray(String... strings) {
		this.strings = strings;
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("{");
		for (int i = 0; i < strings.length; i++) {
			ret.append(strings[i]).append(i != strings.length - 1 ? ", " : "}");
		}
		return ret.toString();
	}

	@NotNull
	@Override
	public String[] getAsStringArray() {
		return strings;
	}

	@Override
	public SerializableValue deepCopy() {
		return new SVStringArray(strings);
	}

	@NotNull
	@Override
	public PropertyType getPropertyType() {
		return PropertyType.Array;
	}

	@Override
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if(o instanceof SVStringArray){
			SVStringArray other = (SVStringArray) o;
			return Arrays.equals(this.strings, other.strings);
		}
		return false;
	}
	
	
}
