from database.connection import Connection
from  bo.item import Item
import cython
import pymssql

class ItemMapper(Connection):
    def __init__(self):
        """
        Aufruf der init-Methode der Superklasse, um damit das Verbindungsobjekt zur Datenbank zu erhalten.
        """
        super().__init__()

    def find_all(self):
        print("Start findAll")
        result = []
        cursor = self._conn.cursor()
        try:
            cursor.execute("SELECT TOP (1000) [itemId],[name],[category],[description],[numberOfUsage],[status],[size],[color] FROM [dbo].[Item]")
            tuples = cursor.fetchall()
            # Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt erstellt.
            for (itemId, name, category, description, numberOfUsage, status, size, color) in tuples:
                item = Item()
                item.setItemID(itemId)
                item.setName(name)
                item.setCategory(category)
                item.setDescription(description)
                item.setNumberOfUsage(numberOfUsage)
                item.setStatus(status)
                item.setSize(size)
                item.setColor(color)
                # Hinzufügen des neuen Objekts zur Ergebnisliste
                result.append(item)
                print("New Item")
        except IndexError:
            print("IndexError in ItemMapper")
            return None
        except pymssql.OperationalError:
            print("OperationalError in ItemMapper")
            return None
        finally:
            self._conn.commit()
            cursor.close()
        # Ergebnisliste zurückgeben
        return result


