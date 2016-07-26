package scene_structure;

public class Condition {
	
	private String name;
	private String value;
	
	public Condition(
			String name,
			String value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
}
