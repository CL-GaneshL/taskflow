
<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div ng-controller="settingsTaskGeneratorCtrl">

        <div class="row col-md-12">

            <div class="col-md-6">

                <!-- ================================================== -->
                <!-- - allocation                                       -->
                <!-- ================================================== -->
                <form class="col-md-12 form-horizontal padding-bottom-20" 
                      role="form"
                      name="allocationForm" >

                    <fieldset>

                        <!-- ================================================== -->
                        <!-- - task generator configuration                     -->
                        <!-- ================================================== -->
                        <div class="row form-group">

                            <input type="submit"
                                   value="Task Generator Configuration"
                                   class="btn btn-primary btn-o pull-left"
                                   data-ng-click="getTaskflowConfiguration()" />

                            <!-- ================================================== -->
                            <!-- - message                                             -->
                            <!-- ================================================== -->
                            <ul class="timeline-xs col-md-12" ng-repeat="log in configuration_logs">

                                <li>
                                    <p>  <span class="fa fa-time"></span> <% log.msg %></p>
                                </li>

                            </ul>

                        </div>

                    </fieldset>
                </form> 

            </div>

            <div class="col-md-6">

                <form class="col-md-12 form-horizontal padding-bottom-20" 
                      role="form"
                      name="resetForm" >

                    <fieldset>

                        <!-- ================================================== -->
                        <!-- - Java Version button                              -->
                        <!-- ================================================== -->
                        <div class="row form-group">

                            <input type="submit"
                                   value="Java Configuration"
                                   class="btn btn-primary btn-o pull-left"
                                   data-ng-click="getJavaVersion()" />

                            <!-- ================================================== -->
                            <!-- - message                                             -->
                            <!-- ================================================== -->
                            <ul class="timeline-xs col-md-12" ng-repeat="log in version_logs">

                                <li>
                                    <p>  <span class="fa fa-time"></span> <% log.msg %></p>
                                </li>

                            </ul>

                        </div>

                    </fieldset>
                </form> 

            </div>

        </div>
    </div>

</div>

