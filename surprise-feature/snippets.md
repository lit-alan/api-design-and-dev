```java


public class CreateReviewRequest {

    private Integer orderId;
    private Integer customerId;
    private Integer rating;
    private String comment;

    public CreateReviewRequest() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
    
}

```



```java


public class ProductV1Response{
    private Long productId;
    private String name;
    private String description;
    private double price;
    private boolean inStock;
    private int stockQuantity;
    private Double averageRating;
    private long reviewCount;
    private String createdAt; //ISO date

    public ProductV1Response() {}

    public ProductV1Response(Long productId, String name, String description, double price, boolean inStock, int stockQuantity, Double averageRating, long reviewCount, String createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
        this.stockQuantity = stockQuantity;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProductV1Response{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", stockQuantity=" + stockQuantity +
                ", averageRating=" + averageRating +
                ", reviewCount=" + reviewCount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}


```


```java

public class ProductV2Response {

    private Long productId;
    private String name;
    private String description;
    private Pricing pricing;
    private boolean inStock;
    private int stockQuantity;
    private Double averageRating;
    private long reviewCount;
    private String createdAt; //YYYY-MM-DD
    private final String apiVersion = "v2";

    public ProductV2Response() {
    }

    public ProductV2Response(Long productId,
                             String name,
                             String description,
                             Pricing pricing,
                             boolean inStock,
                             int stockQuantity,
                             Double averageRating,
                             long reviewCount,
                             String createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.pricing = pricing;
        this.inStock = inStock;
        this.stockQuantity = stockQuantity;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public String toString() {
        return "ProductV2Response{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricing=" + pricing +
                ", inStock=" + inStock +
                ", stockQuantity=" + stockQuantity +
                ", averageRating=" + averageRating +
                ", reviewCount=" + reviewCount +
                ", createdAt='" + createdAt + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                '}';
    }
}

```

```java

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pricing {

    private Money base;
    private Money discounted;
    private boolean discountApplied;

    public Pricing() {
    }

    public Pricing(Money base, Money discounted, boolean discountApplied) {
        this.base = base;
        this.discounted = discounted;
        this.discountApplied = discountApplied;
    }

    public Money getBase() {
        return base;
    }

    public void setBase(Money base) {
        this.base = base;
    }

    public Money getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Money discounted) {
        this.discounted = discounted;
    }

    public boolean isDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(boolean discountApplied) {
        this.discountApplied = discountApplied;
    }
}

```

```java 

import java.math.BigDecimal;

public class Money {

    private BigDecimal amount;
    private String currency;

    public Money() {
    }

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

```

```java


public class ReviewResponse  {
    private Long reviewId;
    private Long productId;
    private Reviewer reviewer;
    private int rating;
    private String comment;
    private String createdAt; //ISO instant
    private boolean verifiedPurchase;
    private ProductRatingSummary productRatingSummary;

    public ReviewResponse(Long reviewId, Long productId, Reviewer reviewer, int rating, String comment, String createdAt, boolean verifiedPurchase, ProductRatingSummary productRatingSummary) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.reviewer = reviewer;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.verifiedPurchase = verifiedPurchase;
        this.productRatingSummary = productRatingSummary;
    }

    public ReviewResponse() {

    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isVerifiedPurchase() {
        return verifiedPurchase;
    }

    public void setVerifiedPurchase(boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
    }

    public ProductRatingSummary getProductRatingSummary() {
        return productRatingSummary;
    }

    public void setProductRatingSummary(ProductRatingSummary productRatingSummary) {
        this.productRatingSummary = productRatingSummary;
    }


    @Override
    public String toString() {
        return "ReviewResponse{" +
                "reviewId=" + reviewId +
                ", productId=" + productId +
                ", reviewer=" + reviewer +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", verifiedPurchase=" + verifiedPurchase +
                ", productRatingSummary=" + productRatingSummary +
                '}';
    }

 
}


```
```java
public class Reviewer {

    private String displayName;
    private String city;

    public Reviewer() {
    }

    public Reviewer(String displayName, String city) {
        this.displayName = displayName;
        this.city = city;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

```

```java

public class ProductRatingSummary {

    private Double averageRating;
    private long reviewCount;

    public ProductRatingSummary() {
    }

    public ProductRatingSummary(Double averageRating, long reviewCount) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }
}


```
