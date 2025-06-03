public class ExampleSecret {
    public static void main(String[] args) {
        String awsSecretKey = "AKIAIOSFODNN7EXAMPLE"; // AWS Access Key (detected)
        String dbPassword = "SuperSecret123";         // Database password (detected)

        System.out.println("Secrets in code are bad!");
    }
}
