
/**
 * CalculateAdminServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.kybpm.web.ws.client;

    /**
     *  CalculateAdminServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CalculateAdminServiceImplServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CalculateAdminServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CalculateAdminServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getTaxRateById method
            * override this method for handling normal response from getTaxRateById operation
            */
           public void receiveResultgetTaxRateById(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.GetTaxRateByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTaxRateById operation
           */
            public void receiveErrorgetTaxRateById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getShippingRateById method
            * override this method for handling normal response from getShippingRateById operation
            */
           public void receiveResultgetShippingRateById(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.GetShippingRateByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getShippingRateById operation
           */
            public void receiveErrorgetShippingRateById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyTaxRate method
            * override this method for handling normal response from modifyTaxRate operation
            */
           public void receiveResultmodifyTaxRate(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.ModifyTaxRateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyTaxRate operation
           */
            public void receiveErrormodifyTaxRate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeShippingRates method
            * override this method for handling normal response from removeShippingRates operation
            */
           public void receiveResultremoveShippingRates(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.RemoveShippingRatesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeShippingRates operation
           */
            public void receiveErrorremoveShippingRates(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyShippingRate method
            * override this method for handling normal response from modifyShippingRate operation
            */
           public void receiveResultmodifyShippingRate(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.ModifyShippingRateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyShippingRate operation
           */
            public void receiveErrormodifyShippingRate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeTaxRates method
            * override this method for handling normal response from removeTaxRates operation
            */
           public void receiveResultremoveTaxRates(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.RemoveTaxRatesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeTaxRates operation
           */
            public void receiveErrorremoveTaxRates(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findAllTaxRates method
            * override this method for handling normal response from findAllTaxRates operation
            */
           public void receiveResultfindAllTaxRates(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.FindAllTaxRatesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findAllTaxRates operation
           */
            public void receiveErrorfindAllTaxRates(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyShippingRateData method
            * override this method for handling normal response from modifyShippingRateData operation
            */
           public void receiveResultmodifyShippingRateData(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.ModifyShippingRateDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyShippingRateData operation
           */
            public void receiveErrormodifyShippingRateData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addTaxRate method
            * override this method for handling normal response from addTaxRate operation
            */
           public void receiveResultaddTaxRate(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.AddTaxRateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addTaxRate operation
           */
            public void receiveErroraddTaxRate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addShippingRate method
            * override this method for handling normal response from addShippingRate operation
            */
           public void receiveResultaddShippingRate(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.AddShippingRateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addShippingRate operation
           */
            public void receiveErroraddShippingRate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findAllShippingRates method
            * override this method for handling normal response from findAllShippingRates operation
            */
           public void receiveResultfindAllShippingRates(
                    com.kybpm.web.ws.client.CalculateAdminServiceImplServiceStub.FindAllShippingRatesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findAllShippingRates operation
           */
            public void receiveErrorfindAllShippingRates(java.lang.Exception e) {
            }
                


    }
    