public class ItemTagConnectionMapper {

/** Klasse als Singleton
	 *Variable durch <code> static </code> nur einmal für Instanzen der Klassen
	 *vorhanden
	 *Sie speichert einzige Instanz der Klasse**/
	private static ItemTagConnectionMapper itemTagConnectionMapper = null;

/** Konstruktor geschützt, es kann keine neue Instanz dieser Klasse mit
     <code>new</code> erzeugt werden**/

     protected ItemTagConnectionMapper() {
	}

	/** Aufruf der statischen Methode durch
	 *<code>ItemTagConnectionMapper.itemTagConnectionMapper()</code>. Singleton: Es kann nur eine
	* Instanz von <code>ItemTagConnectionMapper</code> existieren
	 *@return itemTagConnectionMapper**/

	public static ItemTagConnectionMapper itemTagConnectionMapper() {
		if (itemTagConnectionMapper== null) {
			itemTagConnectionMapper= new ItemTagConnectionMapper();
		}
		return itemTagConnectionMapper;
	}
    /**Daten einer <code>ItemTagConnecion</code>-Objekts aus der Datenbank loeschen.
	* @param m das aus der DB zu loeschende "Objekt"**/

	public void delete(final int itemId, final int tagId) {
		final Connection con = DBConnection.connection();

		try {
			final Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM itemTagConnection WHERE itemId=" + itemId " AND tagId=" + tagId);

		} catch (final SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Einfügen eines ItemTagConnection-Objekts in die Datenbank. 
	 * Es wird auch der Primärschlüssel des übergebenen Objekts geprüft.
	 * @param i das zu speichernde Objekt
	 * @return das übergebene Objekt
	 */
	public ItemTagConnection insert(ItemTagConnecion i){
	Connection con = DBConnection.connection();
	
		try{
			Statement stmt = con.createStatement();

			//Prüfung, welches der momentan höchste Primärschlüsselwert ist.
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM itemTagConnection ");
			
			if(rs.next()){

				//MAXID wird um 1 erhöht
				i.setId(rs.getInt("maxid") +1);
				stmt = con.createStatement();

				PreparedStatement stmt2 = con.prepareStatement("INSERT INTO itemTagConnection (id, tagId, itemId) " + "VALUES(?,?,?)");
				stmt2.setInt(1, i.getId());
				stmt2.setInt(2, i.getTagId());
				stmt2.setInt(3, i.getItemId());

				stmt2.execute();
			
			}catch (SQLException ex){
			 ex.printStackTrace();
			}
				
			return i;

		}
	}

	

}