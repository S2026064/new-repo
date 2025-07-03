package sars.pca.app.common;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "objectType",
    "objectName",
    "contentType",
    "author",
    "properties",
    "content"
})
@Generated("jsonschema2pojo")
public class JsonDocumentDto { 
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("objectName")
    private String objectName;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("author")
    private String author;
     @JsonProperty("properties")
     private List<Properties> properties = new ArrayList<>();
     
   
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("properties")
    public List<Properties> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<Properties> properties) {    
        this.properties = properties;
    }
    @JsonProperty(value = "contentType")
    public String getContentType() {
        return contentType;
    }

    @JsonProperty("contentType")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty("objectName")
    public String getObjectName() {
        return objectName;
    }

    @JsonProperty("objectName")
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("objectType")
    public String getObjectType() {
        return objectType;
    }

    @JsonProperty("objectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }
}
