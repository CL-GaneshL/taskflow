
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Delete Task</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new skill form                                   -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="projectForm" >

            <fieldset>

                <legend>
                    Task :
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
                               value="<% title %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>
                </div>

                <!-- ================================================== -->
                <!-- - Start date                                           -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-9">
                        <input type="text" 
                               class="col-md-12"
                               name="start_date"
                               id="start_date"
                               ng-model="start_date"
                               value="<% start_date %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

                <!-- ================================================== -->
                <!-- -Duration                                          -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Duration :
                    </label>

                    <div class="col-md-9">
                        <input type="text" 
                               class="col-md-12"
                               name="duration"
                               id="duration"
                               ng-model="duration"
                               value="<% duration %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

                <!-- ================================================== -->
                <!-- -Completion                                        -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Completion :
                    </label>

                    <div class="col-md-9">
                        <input type="text" 
                               class="col-md-12"
                               name="completion"
                               id="completion"
                               ng-model="completion"
                               value="<% completion %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

                <!-- ================================================== -->
                <!-- - Nb products planned.                           -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        NBP planned :
                    </label>


                    <div class="col-md-9">
                        <input type="text" 
                               class="col-md-12"
                               name="nb_products_planned"
                               id="nb_products_planned"
                               ng-model="nb_products_planned"
                               value="<% nb_products_planned %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

               <!-- ================================================== -->
                <!-- - Nb products completed.                           -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-right-5">
                        Nb products :
                    </label>


                    <div class="col-md-9">
                        <input type="text" 
                               class="col-md-12"
                               name="nb_products_completed"
                               id="nb_products_completed"
                               ng-model="nb_products_completed"
                               value="<% nb_products_completed %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

                <!-- ================================================== -->
                <!-- -Completed                                      -->
                <!-- ================================================== -->
                <div class="form-group col-md-12">

                    <!-- general non working day -->
                    <div class="row radio clip-radio radio-warning padding-top-25 padding-right-20 text-right">
                        <input type="radio"
                               ng-model="completed"
                               id="completed"
                               value="1" 
                               ng-checked="completed === 1"
                               ng-readonly="true"
                               disabled=""
                               />

                        <label for="completed">
                            Task Completed ?
                        </label>

                    </div>

                </div>

            </fieldset>
        </form>
        <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="delete()">
            Delete
        </button>

    </div>

</div>