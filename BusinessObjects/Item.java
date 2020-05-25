public class Item {

    // Name des Artikels.
	private int ItemId = 0;

	public int getId() {
	    return this.itemId;
	  }
	  //Setzen der Id.
	  public void setId(int id) {
	    this.itemId = id;
	  }
    
      private String category ="";

	  		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

      private String description = "";

	  		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

      private int number_of_usage =0;

	  		public int getNumber_of_usage() {
			return number_of_usage;
		}

		public void setNumber_of_usage(int number_of_usage) {
			this.number_of_usage = number_of_usage;
		}

      private boolean status =false;

	  		public boolean getStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

      private String size ="";

	  		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

      private String color="";

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
}