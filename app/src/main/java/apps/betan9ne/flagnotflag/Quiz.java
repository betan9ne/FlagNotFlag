package apps.betan9ne.flagnotflag;

public class Quiz {
        int id, status;
        String flag;
        String country;

        public Quiz() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Quiz(int id, String flag, String country, int status) {
            this.id = id;
            this.country = country;
            this.flag = flag;
            this.status = status;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
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
