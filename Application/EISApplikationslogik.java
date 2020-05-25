public class EISApplikationslogik {


        *  Referenz auf die MapperKlassen, um die Objekte mit der Datenbank abzugleichen
         */
    
        private ItemMapper iMapper = null;
        
        public void update(Item i) throws IllegalArgumentException {
            iMapper.update(i);
        }
        
        
}