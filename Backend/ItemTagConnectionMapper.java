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

	public void delete(int itemId, int tagId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM itemTagConnection WHERE itemId=" + itemId " AND tagId=" + tagId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}