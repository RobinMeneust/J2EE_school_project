package j2ee_project.model.catalog;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "idProduct")
public class FeaturedProduct extends Product { }
