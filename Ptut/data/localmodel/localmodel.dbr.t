`h�0  R                �� l    
  �   ORMAT_VERSION!�   �    ! &�   able_String! fFORMAT_VERSION!�   �    * /�   able_String! fmain!fFORMAT_VERSION!�   o�� sr jdbm.btree.BTree{G��rߴ  xpsr  jdbm.helper.ComparableComparatorJ˼���  xpw                    xo 7 <�   sersV16!ftable_String! fmain!fFORMAT_VERSION!���o�g3:W<?xml version="1.0" encoding="UTF-8"?>
<metamodel format="1" MetamodelDescriptor.format="1">
  <fragment name="Infrastructure" version="2.1.03" provider="Modeliosoft" providerVersion="3.8.01">
    <dependencies>
      <metamodel_fragment name="modelio.kernel" version="1.0.00"/>
    </dependencies>
    <metaclasses>
      <metaclass name="AbstractDiagram" version="0.0.9054" abstract="true">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <attribute name="UiDataVersion" type="java.lang.Integer"></attribute>
        <attribute name="UiData" type="java.lang.String"></attribute>
        <attribute name="PreviewData" type="java.lang.String"></attribute>
        <dependency name="Represented" min="0" max="-1" navigate="true" weakReference="true">
          <target fragment="Infrastructure" name="Element"/>
          <opposite name="DiagramElement"/>
        </dependency>
        <dependency name="ReferencingSet" min="0" max="-1" navigate="false">
          <target fragment="Infrastructure" name="DiagramSet"/>
          <opposite name="ReferencedDiagram"/>
        </dependency>
        <dependency name="Origin" min="0" max="1" navigate="true">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="Product"/>
        </dependency>
      </metaclass>
      <metaclass name="AbstractProject" version="3.6.00" abstract="true">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <dependency name="DiagramRoot" min="1" max="1" aggregation="Composition" navigate="true">
          <target fragment="Infrastructure" name="DiagramSet"/>
          <opposite name="Owner"/>
        </dependency>
      </metaclass>
      <metaclass name="AbstractResource" version="2.1.00">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <attribute name="MimeType" type="java.lang.String"></attribute>
        <attribute name="StorageInfo" type="java.lang.String"></attribute>
        <dependency name="Type" min="1" max="1" navigate="true">
          <target fragment="Infrastructure" name="ResourceType"/>
          <opposite name="TypedResource"/>
        </dependency>
        <dependency name="Subject" min="0" max="1" navigate="false">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="Attached"/>
        </dependency>
      </metaclass>
      <link_metaclass name="Dependency" version="0.0.9054">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <dependency name="Impacted" min="1" max="1" navigate="false">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="DependsOnDependency"/>
        </dependency>
        <dependency name="DependsOn" min="0" max="1" navigate="true">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="ImpactedDependency"/>
        </dependency>
        <sources>
          <dep name="Impacted"/>
        </sources>
        <targets>
          <dep name="DependsOn"/>
        </targets>
      </link_metaclass>
      <metaclass name="DiagramSet" version="0.0.9054" cmsNode="true">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <dependency name="Sub" min="0" max="-1" aggregation="Composition" navigate="true">
          <target fragment="Infrastructure" name="DiagramSet"/>
          <opposite name="Parent"/>
        </dependency>
        <dependency name="Parent" min="0" max="1" navigate="false">
          <target fragment="Infrastructure" name="DiagramSet"/>
          <opposite name="Sub"/>
        </dependency>
        <dependency name="ReferencedDiagram" min="0" max="-1" navigate="true">
          <target fragment="Infrastructure" name="AbstractDiagram"/>
          <opposite name="ReferencingSet"/>
        </dependency>
        <dependency name="Owner" min="0" max="1" navigate="false">
          <target fragment="Infrastructure" name="AbstractProject"/>
          <opposite name="DiagramRoot"/>
        </dependency>
      </metaclass>
      <metaclass name="Document" version="2.1.00" cmsNode="true">
        <parent fragment="Infrastructure" name="AbstractResource"/>
        <attribute name="Abstract" type="java.lang.String"></attribute>
      </metaclass>
      <metaclass name="DynamicPropertyDefinition" version="1.1.01">
        <parent fragment="Infrastructure" name="PropertyDefinition"/>
      </metaclass>
      <metaclass name="Element" version="0.0.9054" abstract="true">
        <parent fragment="modelio.kernel" name="SmObject"/>
        <dependency name="DiagramElement" min="0" max="-1" navigate="false">
          <target fragment="Infrastructure" name="AbstractDiagram"/>
          <opposite name="Represented"/>
        </dependency>
        <dependency name="AddedToQuery" min="0" max="-1" navigate="false">
          <target fragment="Infrastructure" name="QueryDefinition"/>
          <opposite name="Added"/>
        </dependency>
        <dependency name="causedImpact" min="0" max="-1" navigate="false">
          <target fragment="Infrastructure" name="ImpactLink"/>
          <opposite name="causes"/>
        </dependency>
      </metaclass>
      <metaclass name="EnumeratedPropertyType" version="0.0.9054" cmsNode="true">
        <parent fragment="Infrastructure" name="PropertyType"/>
        <dependency name="Litteral" min="0" max="-1" aggregation="Composition" navigate="true">
          <target fragment="Infrastructure" name="PropertyEnumerationLitteral"/>
          <opposite name="Owner"/>
        </dependency>
        <dependency name="OccurenceConfigParam" min="0" max="-1" navigate="false">
          <target fragment="Infrastructure" name="ModuleParameter"/>
          <opposite name="EnumType"/>
        </dependency>
      </metaclass>
      <metaclass name="ExternElement" version="0.0.00">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <attribute name="Provider" type="java.lang.String"></attribute>
        <attribute name="ExternId" type="java.lang.String"></attribute>
        <attribute name="Location" type="java.lang.String"></attribute>
        <dependency name="Owner" min="1" max="1" navigate="false">
          <target fragment="Infrastructure" name="MethodologicalLink"/>
          <opposite name="ExternElement"/>
        </dependency>
      </metaclass>
      <metaclass name="ExternProcessor" version="0.0.9054">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <attribute name="ClassName" type="java.lang.String"></attribute>
        <dependency name="OwnerQuery" min="0" max="1" navigate="false">
          <target fragment="Infrastructure" name="QueryDefinition"/>
          <opposite name="Processor"/>
        </dependency>
        <dependency name="OwnerValDef" min="0" max="1" navigate="false">
          <target fragment="Infrastructure" name="MatrixValueDefinition"/>
          <opposite name="Processor"/>
        </dependency>
      </metaclass>
      <metaclass name="GraphDiagram" version="2.1.02" cmsNode="true">
        <parent fragment="Infrastructure" name="AbstractDiagram"/>
      </metaclass>
      <metaclass name="ImpactDiagram" version="3.6.00" cmsNode="true">
        <parent fragment="Infrastructure" name="AbstractDiagram"/>
      </metaclass>
      <link_metaclass name="ImpactLink" version="3.6.00">
        <parent fragment="Infrastructure" name="ModelElement"/>
        <dependency name="dependsOn" min="1" max="1" navigate="true">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="impactImpacted"/>
        </dependency>
        <dependency name="impacted" min="1" max="1" navigate="true">
          <target fragment="Infrastructure" name="ModelElement"/>
          <opposite name="impactDependsOn"/>
        </dependency>
        <dependency