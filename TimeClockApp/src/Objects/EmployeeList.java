package Objects;

import java.util.ArrayList;

public class EmployeeList extends ArrayList<Employee> {
    public EmployeeList() {
    }

    public boolean idExists(String id) {
        for(int i=0;i<this.size();i++){
            if(this.get(i).getId()==id){
                return true;
            }
        }
        return false;
    }

}
