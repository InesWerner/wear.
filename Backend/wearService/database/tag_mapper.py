import pymssql
import random

from bo.tag import Tag
from database.connection import Connection


class TagMapper(Connection):


    def __init__(self):
        """
        Aufruf der init-Methode der Superklasse, um damit das Verbindungsobjekt zur Datenbank zu erhalten.
        """
        super().__init__()


    def checkExistence(self,tagID):
        """
        This method checks if a tagID exists in the database.
        :param tagID: Int
        :return: Boolean
        """
        cursor = self._conn.cursor()
        try:
            command = "SELECT COUNT(1) FROM Tag WHERE tagId LIKE '{}'"\
                .format(tagID)
            cursor.execute(command)
            row = cursor.fetchone()
            if row[0] == 1:
                return True
            else:
                return False
        except IndexError:
            print("IndexError in TagMapper; findByTagID ",tagID)
            return None
        except Exception:
            print(Exception.__str__())
            return None
        except pymssql.OperationalError:
            return None
        finally:
            self._conn.commit()
            cursor.close()



    def findByTagID(self, tagID):
        cursor = self._conn.cursor()
        try:
            command = "SELECT tagId, tagFree, tagKey FROM Tag WHERE tagId LIKE '{}'" \
                .format(tagID)
            cursor.execute(command)
            tuples = cursor.fetchall()
            (tagId,tagFree, tagKey) = tuples[0]
            tag = Tag()
            tag.setTagID(tagId)
            tag.setTagFree(tagFree)
            tag.setTagKey(tagKey)
        except IndexError:
            print("IndexError in TagMapper; findByTagID ",tagID)
            return None
        except Exception:
            print(Exception.__str__())
            return None
        except pymssql.OperationalError:
            return None
        finally:
            self._conn.commit()
            cursor.close()
        return tag


    def findByTag(self,tag):
        """
            This method checks if a tag exists in database.
            """
        cursor = self._conn.cursor()
        try:
            command = "SELECT tagId, tagFree, tagKey FROM Tag WHERE tagId LIKE '{}'" \
                .format(tag.getTagID())
            cursor.execute(command)
            tuples = cursor.fetchall()
            (tagId,tagFree, tagKey) = tuples[0]
            tag.setTagID(tagId)
            tag.setTagFree(tagFree)
            tag.setTagKey(tagKey)
        except Exception:
            return None
        except pymssql.OperationalError:
            return None
        finally:
            self._conn.commit()
            cursor.close()
        return tag





    def insert(self,tag):
        """
        This method inserts new tags into the system.

        """
        cursor = self._conn.cursor()
        try:
            randomnumber = "RFID000"+str(random.randint(0,1000));
            # HIER NOCH FRAGEN, OB TAG-ID SCHON VORHANDEN
            command = "INSERT INTO dbo.Tag (tagID, tagKey) VALUES ({},'{}')" \
                .format(tag.getTagID(),randomnumber)
            cursor.execute(command)
            print("Insert new Tag: ",tag.getTagID(),tag.getTagKey())
        except IndexError:
            print("IndexError in TagMapper; Insert ",tag.getTagID())
            return None
        except pymssql.OperationalError:
            print("OperationalError in TagMapper; Insert ",tag.getTagID())

            return None
        finally:
            self._conn.commit()
            cursor.close()
        return tag



    def insertByTagID(self,tagID):
        """
        This method inserts new tags into the system.

        """
        cursor = self._conn.cursor()
        try:
            randomnumber = "RFID000"+ str(random.randint(0,1000));
            # HIER NOCH FRAGEN, OB TAG-ID SCHON VORHANDEN
            command = "INSERT INTO dbo.Tag (tagID, tagKey) VALUES ({},'{}')" \
                .format(tagID,randomnumber)
            cursor.execute(command)
            print("Insert new Tag: ",tagID,randomnumber)
        except IndexError:
            print("IndexError in TagMapper; Insert ",tagID)
        except pymssql.OperationalError:
            print("OperationalError in TagMapper; Insert ",tagID)
        finally:
            self._conn.commit()
            cursor.close()





    def update(self,tag):
        """
        This method changes the the tagStatus of a existing tag. So if the tagStatus is 1 (which means 'true') the tag/item should
        be physically in the wardrobe. If the tagStatus is set to 0 (which means 'false') the tag/item should be physically outside
        of the wardrobe.

        """
        cursor = self._conn.cursor()
        try:
            command = "UPDATE Item SET status = (CASE WHEN status = 1 THEN 0 ELSE 1 END ) WHERE tagID={}" \
                .format(tag.getTagID())
            cursor.execute(command)
            print("Update Tag: ",tag.getTagID())
        except IndexError:
            print("IndexError in TagMapper; Update ",tag.getTagID())
            return None
        except pymssql.OperationalError:
            print("OperationalError in TagMapper; Update ",tag.getTagID())
            print(pymssql.OperationalError.__str__())

            return None
        finally:
            self._conn.commit()
            cursor.close()
        return tag



    def updateByTagID(self,tagID):
        """
        This method changes the the tagStatus of a existing tag. So if the tagStatus is 1 (which means 'true') the tag/item should
        be physically in the wardrobe. If the tagStatus is set to 0 (which means 'false') the tag/item should be physically outside
        of the wardrobe.

        """
        cursor = self._conn.cursor()
        try:
            command = "UPDATE Item SET status = (CASE WHEN status = 1 THEN 0 ELSE 1 END ) WHERE tagID={}" \
                .format(tagID)
            cursor.execute(command)
            print("Update Tag: ",tagID)
        except IndexError:
            print("IndexError in TagMapper; Update ",tagID)
        except pymssql.OperationalError:
            print("OperationalError in TagMapper; Update ",tagID)
            print(pymssql.OperationalError.__str__())
        finally:
            self._conn.commit()
            cursor.close()