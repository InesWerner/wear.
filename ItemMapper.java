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

    
}