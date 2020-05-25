import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ItemMapper {


	/**Klasse ItemMapper als Singleton
	 *Variable durch <code> static </code> nur einmal f�r Instanzen der Klassen
	 *vorhanden
	 *Sie speichert einzige Instanz der Klasse **/
	private static ItemMapper itemMapper = null;


    /** Konstruktor gesch�tzt, es kann keine neue Instanz dieser Klasse mit <code>new</code> erzeugt werden**/

	protected ItemMapper() {
	}

/**Aufruf der statischen Methode durch <code>ArticleMapper.articleMapper()</code>. Singleton: Es kann nur eine 
 *Instanz von <code>ArticleMapper</code> existieren
 * @return articleMapper
 * 
 */

	public static ItemMapper itemMapper() {
		if (itemMapper== null) {
			itemMapper= new ItemMapper();
		}
		return itemMapper;
	}


	public Item update(Item i) {
		Connection con = DBConnection.connection();
	
		try {
		  Statement stmt = con.createStatement();
	
		  stmt.executeUpdate("UPDATE item "
				  + "SET ItemId=" + i.getId() +"," +
		   " category=\"" + i.getCategory() + "\",  description=\"" + i.getDescription() +"\", " + "number_of_usage=\"" + i.getNumber_of_usage() +"\", " + "status=\"" + i.getStatus() +"\", " + "size=\"" + i.getSize() +"\", " + "color=\"" + i.getColor()+ "\" WHERE id=" + i.getId());
	
		}
		catch (SQLException e2) {
		  e2.printStackTrace();
		}
	
		// i zueruck geben
		return i;
	  }
	


	public Vector<Item> findItemByColor(String color){
		  Connection con = DBConnection.connection();
		  Vector<Item> results = new Vector<Item>;

		  try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT itemId, category, description, 
			number_of_usage, status, size, color FROM item WHERE color=" +"'" + color + "'" );
			
			while (rs.next()) {
			Item i = new Item();
			i.setItemId(rs.getInt("id"));
			i.setCategory(rs.getString("category"));
			i.setDescription(rs.getString("description"));
			i.setNumber_of_usage(rs.getInt("number_of_usage"));
			i.setStatus(rs.getBoolean("status"));
			i.setSize(rs.getString("size"));
			i.setColor(rs.getString("color"));
			
			results.addElement(e);
			}
			}
			catch(SQLException ex){
			ex.printStackTrace();
			}
			
			return results;
			}

	  }



}