
<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div ng-controller="settingsHourlyCostCtrl">

        <div class="row col-md-12">

            <div class="col-md-6">

                <!-- ================================================== -->
                <!-- - allocation                                       -->
                <!-- ================================================== -->
                <form class="col-md-12 form-horizontal padding-bottom-20" 
                      role="form"
                      name="hourlyCostForm" >

                    <fieldset>

                        <legend>
                            Hourly Cost
                        </legend>

                        <!-- ================================================== -->
                        <!-- - hourly cost                                      -->
                        <!-- ================================================== -->
                        <div class="row col-md-12 form-group">

                            <div class="col-md-7">

                                <span class="input-icon">

                                    <i class="fa fa-rupee"></i>

                                    <input type="text" 
                                           class="form-control"
                                           name="hourlyCost"
                                           ng-value ="hourlyCost"
                                           ng-readonly="true"
                                           disabled=""
                                           />
                                </span> 
                                
                            </div>

                            <div class="col-md-5">
                                <input type="submit"
                                       value="Update"
                                       class="btn btn-primary btn-o pull-right"
                                       ng-click="updateHourlyCost()"
                                       />
                            </div>

                        </div>

                    </fieldset>
                </form> 

            </div>

        </div>
    </div>

</div>

