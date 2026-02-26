```java
import java.util.List;

public class CreateOrderRequest {

    private Integer customerId;
    private String paymentMethod;
    private String discountCode; // optional
    private List<CreateOrderItemRequest> items;

    public CreateOrderRequest() {}

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDiscountCode() { return discountCode; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }

    public List<CreateOrderItemRequest> getItems() { return items; }
    public void setItems(List<CreateOrderItemRequest> items) { this.items = items; }
}

```

```java
public class CreateOrderItemRequest {

    private Integer productId;
    private Integer quantity;

    public CreateOrderItemRequest() {}

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}


```


```java
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderV1Response {

    private Integer orderId;
    private String orderDate;            //YYYY-MM-DD
    private String status;
    private String paymentMethod;

    private CustomerSummary customer;
    private List<OrderItemResponse> items;

    private int itemCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String discountCode;

    private BigDecimal subtotal;


    private BigDecimal total;
    private boolean totalsMatch;

    private List<Map<String, String>> links;

    public OrderV1Response() {}



    public OrderV1Response(Integer orderId,
                           String orderDate,
                           String status,
                           String paymentMethod,
                           CustomerSummary customer,
                           List<OrderItemResponse> items,
                           int itemCount,
                           String discountCode,
                           BigDecimal subtotal,
                           BigDecimal total,
                           boolean totalsMatch,
                           List<Map<String, String>> links) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.items = items;
        this.discountCode = discountCode;
        this.itemCount = itemCount;
        this.subtotal = subtotal;
        this.total = total;
        this.totalsMatch = totalsMatch;
        this.links = links;
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public CustomerSummary getCustomer() { return customer; }
    public void setCustomer(CustomerSummary customer) { this.customer = customer; }

    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }

    public String getDiscountCode() { return discountCode; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public boolean isTotalsMatch() { return totalsMatch; }
    public void setTotalsMatch(boolean totalsMatch) { this.totalsMatch = totalsMatch; }

    public List<Map<String, String>> getLinks() { return links; }
    public void setLinks(List<Map<String, String>> links) { this.links = links; }
}


```



```java
import java.util.List;
import java.util.Map;

public class OrderV2Response {

    private final String apiVersion = "v2";
    private Integer orderId;
    private String orderDate;  //YYYY-MM-DD
    private String status;
    private String paymentMethod;

    private CustomerSummary customer;
    private List<OrderItemResponse> items;

    private int itemCount;

    private PricingSummaryV2 pricing;

    private List<Map<String, String>> links;

    public OrderV2Response() {}

    public OrderV2Response(Integer orderId,
                           String orderDate,
                           String status,
                           String paymentMethod,
                           CustomerSummary customer,
                           List<OrderItemResponse> items,
                           int itemCount,
                           PricingSummaryV2 pricing,
                           List<Map<String, String>> links) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.items = items;
        this.itemCount = itemCount;
        this.pricing = pricing;
        this.links = links;
    }

    public String getApiVersion() { return apiVersion; }
   
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public CustomerSummary getCustomer() { return customer; }
    public void setCustomer(CustomerSummary customer) { this.customer = customer; }

    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }

    public PricingSummaryV2 getPricing() { return pricing; }
    public void setPricing(PricingSummaryV2 pricing) { this.pricing = pricing; }

    public List<Map<String, String>> getLinks() { return links; }
    public void setLinks(List<Map<String, String>> links) { this.links = links; }
}


```



```java
public class CustomerSummary {

    private Integer customerId;
    private String displayName; //first name + initial of last name
    private String city;

    public CustomerSummary() {}

    public CustomerSummary(Integer customerId, String displayName, String city) {
        this.customerId = customerId;
        this.displayName = displayName;
        this.city = city;
    }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}


```



```java
import java.math.BigDecimal;

public class OrderItemResponse {

    private Integer productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

    public OrderItemResponse() {}

    public OrderItemResponse(Integer productId,
                             String productName,
                             int quantity,
                             BigDecimal unitPrice,
                             BigDecimal lineTotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getLineTotal() { return lineTotal; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}


```






```java
public class DiscountSummary {

    private String code;

    public DiscountSummary() {}

    public DiscountSummary(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

}


```

```java
import java.math.BigDecimal;

public class ConsistencyCheck {

    private boolean totalsMatch;
    private BigDecimal delta;

    public ConsistencyCheck() {}

    public ConsistencyCheck(boolean totalsMatch, BigDecimal delta) {
        this.totalsMatch = totalsMatch;
        this.delta = delta;
    }

    public boolean isTotalsMatch() { return totalsMatch; }
    public void setTotalsMatch(boolean totalsMatch) { this.totalsMatch = totalsMatch; }

    public BigDecimal getDelta() { return delta; }
    public void setDelta(BigDecimal delta) { this.delta = delta; }
}

```


```java

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingSummaryV2 {

    private String currency;
    private BigDecimal subtotal;

    private boolean discountApplied;
    private DiscountSummary discount; //omit if discountApplied=false

    private BigDecimal total;

    private ConsistencyCheck consistencyCheck;

    public PricingSummaryV2() {}

    public PricingSummaryV2(String currency,
                            BigDecimal subtotal,
                            boolean discountApplied,
                            DiscountSummary discount,
                            BigDecimal total,
                            ConsistencyCheck consistencyCheck) {
        this.currency = currency;
        this.subtotal = subtotal;
        this.discountApplied = discountApplied;
        this.discount = discount;
        this.total = total;
        this.consistencyCheck = consistencyCheck;
    }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public boolean isDiscountApplied() { return discountApplied; }
    public void setDiscountApplied(boolean discountApplied) { this.discountApplied = discountApplied; }

    public DiscountSummary getDiscount() { return discount; }
    public void setDiscount(DiscountSummary discount) { this.discount = discount; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public ConsistencyCheck getConsistencyCheck() { return consistencyCheck; }
    public void setConsistencyCheck(ConsistencyCheck consistencyCheck) { this.consistencyCheck = consistencyCheck; }
}

```
