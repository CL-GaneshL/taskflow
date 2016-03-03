
<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div ng-controller="taskAllocationCtrl">

        <div class="row col-md-12">

            <div class="col-md-6">

                <!-- ================================================== -->
                <!-- - allocation                                       -->
                <!-- ================================================== -->
                <div class="col-md-12">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="allocationForm" >

                        <fieldset>

                            <legend>
                                Allocation
                            </legend>

                            <!-- ================================================== -->
                            <!-- - allocate button                              -->
                            <!-- ================================================== -->
                            <div class="row form-group">

                                <div class="col-md-4 padding-40 text-right">

                                    <input type="submit"
                                           value="Allocate"
                                           class="btn btn-primary btn-o pull-right"
                                           data-ng-click="allocateTasks()" />

                                </div>

                                <div class="row col-md-7 text-muted text-small">
                                    <p><%generate_message%></p>
                                </div>

                            </div>

                        </fieldset>
                    </form> 

                </div>
                <!-- ================================================== -->

            </div>

            <div class="col-md-6">

                <!-- ================================================== -->
                <!-- - reset                                       -->
                <!-- ================================================== -->
                <div class="col-md-12">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="resetForm" >

                        <fieldset>

                            <legend>
                                Reset ( Testing only ! )
                            </legend>

                            <!-- ================================================== -->
                            <!-- - reset button                              -->
                            <!-- ================================================== -->
                            <div class="row">
                                <div class="row form-group">

                                    <div class="col-md-4 padding-40 text-right">

                                        <input type="submit"
                                               value="Reset"
                                               class="btn btn-primary btn-o pull-right"
                                               data-ng-click="resetTasks()" />

                                    </div>
                                    
                                    <div class="row col-md-7 text-muted text-small">
                                        <p><%reset_message%></p>
                                    </div>
                                    
                                </div>

                        </fieldset>
                    </form> 

                </div>
                <!-- ================================================== -->

            </div>

        </div>
    </div>

</div>

