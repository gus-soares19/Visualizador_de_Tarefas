package models;

public class Information {
	private String name;
	private long value;
	private boolean megabytes;

	public Information() {
	}

	public Information(String name, long value) {
		this.setName(name);

		if (isInBytes(name)) {
			value = toMegaBytes(value);
			this.setMegabytes(true);
		}

		this.setValue(value);
	}

	public static boolean isInBytes(String name) {
		return name.contains("Size") || name.contains("Usage") ? true : false;
	}

	private long toMegaBytes(long value) {
		return (long) (value / Math.pow(1024, 2));
	}

	@Override
	public String toString() {
		String str = "Descrição: " + this.getName() + " | Valor: " + this.getValue();

		return this.isMegabytes() ? str += "MB" : str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public boolean isMegabytes() {
		return megabytes;
	}

	public void setMegabytes(boolean megabytes) {
		this.megabytes = megabytes;
	}

}
