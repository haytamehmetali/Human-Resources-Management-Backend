package owl.humanresourcesmanagement.enums.user;

public enum EUserRole {
     MANAGER("Yönetici"),
     PERSONAL("Personel");
     
     private final String description;
     
     EUserRole(String description) {
          this.description = description;
     }
     
     public String getDescription() {
          return description;
     }
}
