import java.util.Vector;

public class EISApplikationslogik {


        *  Referenz auf die MapperKlassen, um die Objekte mit der Datenbank abzugleichen
         */
    
        private ItemMapper iMapper = null;
        
        public void update(Item i) throws IllegalArgumentException {
            iMapper.update(i);
        }
        
         //Aufruf ItemMapper findItemsByColor
        public Vector<Item> getItemsByColor(String color) throws IllegalArgumentException{
           return this.iMapper.findItemsByColor(color);
        }

        //Aufruf ItemMapper findItemsBySize
        public Vector<Item> getItemsBySize(String size) throws IllegalArgumentException{
            return this.iMapper.findItemsBySize(size);
         }

         public Vector<Item> getItemsByStatus(Boolean status) throws IllegalArgumentException {
            return this.iMapper.filterItemsByStatus(status);
        }


        // Wenn ein Item entfernt wird muss die Methode zum Löschen der ItemTagConnection aufgerufen werden
        //Wenn ein Item erzeugt wird muss eine ItemTagConnection erzeugt werden 
}