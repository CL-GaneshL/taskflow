
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Validation</h3>
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
              name="templateForm" >

            <fieldset>

                <legend>
                    Create the following Project Template ?
                </legend>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Reference :
                    </label>

                    <div class="col-md-8">

                        <input type="text"
                               class="col-md-12"
                               name="templateReference" 
                               id="templateDesignation"
                               ng-model="templateReference"
                               value="<% templateReference %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Designation :
                    </label>

                    <div class="col-md-8">

                        <input type="text" 
                               class="col-md-12"
                               name="templateDesignation"
                               id="templateDesignation"
                               ng-model="templateDesignation"
                               value="<% templateDesignation %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>
            </fieldset>
            <!-- ================================================== -->
        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="createTemplate()">
            Create
        </button>

    </div>

</div>