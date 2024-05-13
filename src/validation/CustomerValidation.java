package validation;

public class CustomerValidation {
    public static boolean validateCustomer(String email, String firstName, String lastName) {
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if(firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }


        return true;
    }
}
