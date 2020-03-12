package apps.betan9ne.flagnotflag;

public class Quiz {
        int id;
        String flag;
        String country;

        public Quiz() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Quiz(int id, String flag, String country) {
            this.id = id;
            this.country = country;
            this.flag = flag;
        }

        public void setId(int id) {
                this.id = id;
        }

        public int getId() {
                return id;
        }

        public String getCountry() {
                return country;
        }

        public String getFlag() {
                return flag;
        }

        public void setCountry(String country) {
                this.country = country;
        }

        public void setFlag(String flag) {
                this.flag = flag;
        }
}
