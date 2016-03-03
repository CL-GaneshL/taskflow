
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Templates</h1>
        </div>

    </div>
</section>


<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div ng-controller="templatesCtrl">

        <div class="row col-md-12">

            <div class="col-md-6">

                <!-- ================================================== -->
                <!-- - new template form                                 -->
                <!-- ================================================== -->
                <div class="col-md-12">

                    <form class="form-horizontal padding-bottom-20" 
                          role="form"
                          name="templateForm" >

                        <fieldset>

                            <legend>
                                Create a Template
                            </legend>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Reference :
                                </label>

                                <div class="col-md-8">

                                    <input type="text" 
                                           class="form-control"
                                           name="newTemplateReference"
                                           ng-model="newTemplateReference"
                                           placeholder="Enter Template Reference"
                                           ng-trim="true"
                                           required
                                           />
                                </div>
                            </div>

                            <div class="form-group">

                                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                                    Designation :
                                </label>

                                <div class="col-md-8">

                                    <input type="text" 
                                           class="form-control"
                                           name="newTemplateDesignation"
                                           ng-model="newTemplateDesignation"
                                           placeholder="Enter Template Designation"
                                           ng-trim="true"
                                           required
                                           />
                                </div>

                            </div>

                            <!-- ================================================== -->
                            <!-- - new template button                                 -->
                            <!-- ================================================== -->
                            <div class="form-group">

                                <div class="col-md-offset-7 col-md-4 text-right">

                                    <input type="submit"
                                           value="New Template"
                                           class="btn btn-primary btn-o pull-right"
                                           data-ng-click="createTemplate()" />

                                </div>

                            </div>

                        </fieldset>
                    </form> 

                </div>
                <!-- ================================================== -->

            </div>

            <!-- ================================================== -->
            <!-- - template table                                     -->
            <!-- ================================================== -->                    
            <div class="col-md-6">

                <table class="table table-condensed table-striped table-hover">

                    <thead>
                        <tr>
                            <th class="col-md-3">Reference</th>
                            <th class="hidden-xs col-md-7">Designation</th>
                        </tr>
                    </thead>

                    <tbody>

                        <tr ng-repeat="template in templates"  >

                            <td class="col-md-3"> <% template.reference %> </td>
                            <td class="col-md-7"> <% template.designation %> </td>

                            <!-- ================================================== -->
                            <!-- - edit and remove icons                            -->
                            <!-- ================================================== -->  
                            <td class="col-md-3">
                                <div class="visible-md visible-lg hidden-sm hidden-xs">

                                    <a href="#"
                                       class="btn btn-transparent btn-xs" 
                                       tooltip-placement="top" 
                                       uib-tooltip="Edit Template"
                                       ng-click="editTemplate( <% template.id %> )" >
                                        <i class="fa fa-pencil"></i>
                                    </a>

                                    <a href="#"
                                       class="btn btn-transparent btn-xs" 
                                       tooltip-placement="top"
                                       ng-click="closeTemplate( <% template.id %> )"
                                       uib-tooltip="Close"
                                       >
                                        <i class="fa fa-times fa fa-white"></i>
                                    </a>

                                    <div class="visible-xs visible-sm hidden-md hidden-lg">
                                        <div class="btn-group" uib-dropdown>

                                            <a href="#" type="button"
                                               class="btn btn-primary btn-sm dropdown-toggle"
                                               uib-dropdown-toggle>
                                                <i class="fa fa-cog"></i>&nbsp;<span class="caret"></span>
                                            </a>

                                            <ul class="dropdown-menu pull-right dropdown-light" role="menu">
                                                <li>
                                                    <a href="#"  
                                                       ng-click="editTemplate( <% template.id %> )" >
                                                        Edit
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" 
                                                       ng-click="closeTemplate( <% template.id %> )" >
                                                        Remove
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>

                            </td>
                            <!-- ================================================== -->

                        </tr>

                    </tbody>
                </table>
                
                <div class="row col-md-12 text-muted text-small"
                     ng-show="templates.length === 0"
                     >
                    <p>No open project templates !</p>
                </div>
                
            </div>
        </div>
    </div>

</div>


<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->
