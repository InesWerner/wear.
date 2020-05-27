public class Item {

	private int itemId;
	private String name;
	private String category;
	private String description;
	private int numberOfUsage;
	private boolean status;
	private String size;
	private String color;


	
	public Item() {
	}

	public Item(String name, String category, String description, String size, String color) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.size = size;
		this.color = color;
	}



	public Item(int itemId, String name, String category, String description, int numberOfUsage, boolean status,
			String size, String color) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.description = description;
		this.numberOfUsage = numberOfUsage;
		this.status = status;
		this.size = size;
		this.color = color;
	}

	public int getId() {
		return this.itemId;
	}

	public void setId(int id) {
		this.itemId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfUsage() {
		return numberOfUsage;
	}

	public void setNumberOfUsage(int number_of_usage) {
		this.numberOfUsage = number_of_usage;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString(){
		return "############## \n ItemID: " + this.itemId + " \n Name: " +  this.name 
		+ " \n Category: " + this.category + " \n Description: " 
		+ this.description + " \n  NumberOfUsage: " + this.numberOfUsage + " \n Status" 
		+ this.status + "\n Size: " + this.size + "\n Color: " + this.color ;
	}

}