public class EISApplikationslogik {


        *  Referenz auf die MapperKlassen, um die Objekte mit der Datenbank abzugleichen
         *  @autor InesWerner
         */
    
        private EntryMapper eMapper = null;
        
        public void update(Entry e) throws IllegalArgumentException {
            eMapper.update(equals(e));
        }
        
        
}