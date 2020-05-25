import java.util.Vector;

public class EISApplikationslogik {


        *  Referenz auf die MapperKlassen, um die Objekte mit der Datenbank abzugleichen
         */
    
        private ItemMapper iMapper = null;
        
        public void update(Item i) throws IllegalArgumentException {
            iMapper.update(i);
        }
        
         //Aufruf ItemMapper findItemsByColor
        public Vector<Item> getItemsByColor(String color){
           return this.iMapper.findItemsByColor(color) 
        }

        //Aufruf ItemMapper findItemsBySize
        public Vector<Item> getItemsBySize(String size){
            return this.iMapper.findItemsBySize(size) 
         }
}