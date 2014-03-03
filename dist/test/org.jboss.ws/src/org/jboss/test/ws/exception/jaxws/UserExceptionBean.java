package org.jboss.test.ws.exception.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "http://server.exception.samples.jaxws.ws.test.jboss.org/", name = "UserException")
@XmlType(namespace = "http://server.exception.samples.jaxws.ws.test.jboss.org/", name = "UserException", propOrder = {
    "errorCategory",
    "errorCode",
    "message"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExceptionBean {

    @XmlElement(namespace = "", name = "errorCategory")
    private String errorCategory;
    @XmlElement(namespace = "", name = "errorCode")
    private int errorCode;
    @XmlElement(namespace = "", name = "message")
    private String message;

    public String getErrorCategory() {
        return this.errorCategory;
    }

    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
