
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Create Holiday</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new skill form                                   -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" role="form" >

            <fieldset>

                <legend>
                    Holiday :
                </legend>

                <!-- ================================================== -->
                <!-- -title                                             -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Title :
                    </label>

                    <div class="col-md-9">
                        <input type="text"
                               class="col-md-12"
                               name="title" 
                               id="title"
                               ng-model="title"
                               placeholder ="Enter a title..."
                               required />
                    </div>

                </div>

                <!-- ================================================== -->
                <!-- - Start date                                       -->
                <!-- ================================================== -->
                <div class="form-group row col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-9">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="start_date" 
                                   ng-init="startOpen = false"
                                   class="form-control underline"
                                   ng-click="startOpen = !startOpen" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen"     
                                   min-date="min_date"
                                   max-date="max_date" 
                                   close-text="Close" 
                                   placeholder ="Enter a start date ..."
                                   />
                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

                <!-- ================================================== -->
                <!-- - End date                                       -->
                <!-- ================================================== -->
                <div class="form-group row col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        End Date :
                    </label>

                    <div class="col-md-9">

                        <span class="input-icon">
                            <input type="text" 
                                   ng-model="end_date" 
                                   ng-init="startOpen2 = false"
                                   class="form-control underline"
                                   ng-click="startOpen2 = !startOpen2" 
                                   uib-datepicker-popup="fullDate"             
                                   is-open="startOpen2"     
                                   min-date="min_date"
                                   max-date="max_date" 
                                   close-text="Close" 
                                   placeholder ="Enter a end date ..."
                                   />
                            <i class="ti-calendar"></i>
                        </span>

                    </div>

                </div>

            </fieldset>

        </form>
        <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-md-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="cancel()">
                Cancel
            </button>

            <button class="btn btn-primary btn-o" ng-click="save()">
                Save
            </button>

        </div>

    </div>

</div>