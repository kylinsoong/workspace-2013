
package org.jboss.test.ws.exception;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jboss.test.ws.exception package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ThrowRuntimeExceptionResponse_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwRuntimeExceptionResponse");
    private final static QName _ThrowSoapFaultExceptionResponse_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwSoapFaultExceptionResponse");
    private final static QName _UserException_QNAME = new QName("http://exception.ws.test.jboss.org/", "UserException");
    private final static QName _ThrowApplicationExceptionResponse_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwApplicationExceptionResponse");
    private final static QName _ThrowRuntimeException_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwRuntimeException");
    private final static QName _ThrowSoapFaultException_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwSoapFaultException");
    private final static QName _ThrowApplicationException_QNAME = new QName("http://exception.ws.test.jboss.org/", "throwApplicationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jboss.test.ws.exception
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ThrowApplicationExceptionResponse }
     * 
     */
    public ThrowApplicationExceptionResponse createThrowApplicationExceptionResponse() {
        return new ThrowApplicationExceptionResponse();
    }

    /**
     * Create an instance of {@link ThrowRuntimeExceptionResponse }
     * 
     */
    public ThrowRuntimeExceptionResponse createThrowRuntimeExceptionResponse() {
        return new ThrowRuntimeExceptionResponse();
    }

    /**
     * Create an instance of {@link ThrowSoapFaultExceptionResponse }
     * 
     */
    public ThrowSoapFaultExceptionResponse createThrowSoapFaultExceptionResponse() {
        return new ThrowSoapFaultExceptionResponse();
    }

    /**
     * Create an instance of {@link ThrowApplicationException }
     * 
     */
    public ThrowApplicationException createThrowApplicationException() {
        return new ThrowApplicationException();
    }

    /**
     * Create an instance of {@link ThrowRuntimeException }
     * 
     */
    public ThrowRuntimeException createThrowRuntimeException() {
        return new ThrowRuntimeException();
    }

    /**
     * Create an instance of {@link UserException }
     * 
     */
    public UserException createUserException() {
        return new UserException();
    }

    /**
     * Create an instance of {@link ThrowSoapFaultException }
     * 
     */
    public ThrowSoapFaultException createThrowSoapFaultException() {
        return new ThrowSoapFaultException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowRuntimeExceptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwRuntimeExceptionResponse")
    public JAXBElement<ThrowRuntimeExceptionResponse> createThrowRuntimeExceptionResponse(ThrowRuntimeExceptionResponse value) {
        return new JAXBElement<ThrowRuntimeExceptionResponse>(_ThrowRuntimeExceptionResponse_QNAME, ThrowRuntimeExceptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowSoapFaultExceptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwSoapFaultExceptionResponse")
    public JAXBElement<ThrowSoapFaultExceptionResponse> createThrowSoapFaultExceptionResponse(ThrowSoapFaultExceptionResponse value) {
        return new JAXBElement<ThrowSoapFaultExceptionResponse>(_ThrowSoapFaultExceptionResponse_QNAME, ThrowSoapFaultExceptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "UserException")
    public JAXBElement<UserException> createUserException(UserException value) {
        return new JAXBElement<UserException>(_UserException_QNAME, UserException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowApplicationExceptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwApplicationExceptionResponse")
    public JAXBElement<ThrowApplicationExceptionResponse> createThrowApplicationExceptionResponse(ThrowApplicationExceptionResponse value) {
        return new JAXBElement<ThrowApplicationExceptionResponse>(_ThrowApplicationExceptionResponse_QNAME, ThrowApplicationExceptionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowRuntimeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwRuntimeException")
    public JAXBElement<ThrowRuntimeException> createThrowRuntimeException(ThrowRuntimeException value) {
        return new JAXBElement<ThrowRuntimeException>(_ThrowRuntimeException_QNAME, ThrowRuntimeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowSoapFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwSoapFaultException")
    public JAXBElement<ThrowSoapFaultException> createThrowSoapFaultException(ThrowSoapFaultException value) {
        return new JAXBElement<ThrowSoapFaultException>(_ThrowSoapFaultException_QNAME, ThrowSoapFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ThrowApplicationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.test.jboss.org/", name = "throwApplicationException")
    public JAXBElement<ThrowApplicationException> createThrowApplicationException(ThrowApplicationException value) {
        return new JAXBElement<ThrowApplicationException>(_ThrowApplicationException_QNAME, ThrowApplicationException.class, null, value);
    }

}
