package edu.umd.cs.guitar.guitestrunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import edu.unl.cse.shuang.guts.rule.DefaultRule;
import edu.unl.cse.shuang.guts.rule.StartupRule;
import edu.unl.cse.shuang.guts.rule.StepPosition;
import edu.unl.cse.shuang.guts.rule.TerminationRule;
import edu.unl.cse.shuang.guts.rule.TestCaseStepRule;

public class TestCaseStepReaderWriter {
    public TestCaseStepReaderWriter() {
        // Empty.
    }

    public TestCaseStep[] createTestCaseSteps(File efgFile, File guiFile) throws TestRunnerException {
        try {
            List<TestCaseStep> result = new ArrayList<TestCaseStep>();
            // Read in XML files, one for Event-Flow Graph, the other for the
            // structure of the GUIs. Find out the corresponding attributes for
            // the events of EFG in the structure.
            Builder b = new Builder();
            Document efg = b.build(efgFile);
            Document gui = b.build(guiFile);
            Nodes steps = efg.query("//Event");
            for (int i = 0; i < steps.size(); ++i) {
                Node tmp = steps.get(i);
                Element stepElement = null;
                if (tmp instanceof Element) {
                    stepElement = (Element) tmp;
                } else {
                    throw new TestRunnerException("Invalid input file with different meanings for events!");
                }
                String name = stepElement.getFirstChildElement("Component").getValue();
                String q = "//Attributes[Property/Name=\"Title\" and Property/Value=\"" + name + "\"]";
                Nodes attributes = gui.query(q);
                if (attributes.size() < 1) {
                    throw new TestRunnerException("Cannot find attributes for " + name + "!");
                }
                tmp = attributes.get(0);
                Element attributesElement = null;
                if (tmp instanceof Element) {
                    attributesElement = (Element) tmp;
                } else {
                    throw new TestRunnerException("Invalid input file with different meanings for GUI structure!");
                }
                TestCaseStep step = createTestCaseStep(stepElement, attributesElement);
                result.add(step);
            }

            return result.toArray(new TestCaseStep[0]);
        } catch (Exception e) {
            throw new TestRunnerException(e);
        }
    }

    /**
     * Construct a step from a step element.
     * 
     * @param stepNode
     * @return
     */
    protected TestCaseStep createTestCaseStep(Element stepElem) {
        TestCaseStep result = new TestCaseStep();

        Element windowElem = stepElem.getFirstChildElement("Window");
        WindowName windowName = new WindowName(windowElem.getValue());
        result.setWindowName(windowName);

        Element componentElem = stepElem.getFirstChildElement("Component");
        ComponentId componentId = new ComponentId(componentElem.getValue());
        result.setComponentId(componentId);

        Element actionElem = stepElem.getFirstChildElement("Action");
        String actionText = actionElem.getValue();
        result.setAction(new Action(actionText));

        String actionType = "SYSTEM INTERACTION";
        Elements properties = stepElem.getFirstChildElement("Attributes").getChildElements("Property");
        for (int i = 0; i < properties.size(); ++i) {
            Element property = properties.get(i);
            Element name = property.getFirstChildElement("Name");
            Element value = property.getFirstChildElement("Value");
            if (name.getValue().equals("Type")) {
                actionType = value.getValue();
            } else if (name.getValue().equals("Class")) {
                result.setComponentClassName(value.getValue());
            } else if (name.getValue().equals("ToolTip")) {
                result.setTooltip(value.getValue());
            }
        }
        result.setActionType(actionType);

        return result;
    }

    protected TestCaseStep createTestCaseStep(Element event, Element attributes) {
        TestCaseStep result = new TestCaseStep();

        Element windowElem = event.getFirstChildElement("Window");
        WindowName windowName = new WindowName(windowElem.getValue());
        result.setWindowName(windowName);

        Element componentElem = event.getFirstChildElement("Component");
        ComponentId componentId = new ComponentId(componentElem.getValue());
        result.setComponentId(componentId);

        Element actionElem = event.getFirstChildElement("Action");
        String actionText = actionElem.getValue();
        result.setAction(new Action(actionText));

        String actionType = "SYSTEM INTERACTION";
        Elements properties = attributes.getChildElements("Property");
        for (int i = 0; i < properties.size(); ++i) {
            Element property = properties.get(i);
            Element name = property.getFirstChildElement("Name");
            Element value = property.getFirstChildElement("Value");
            if (name.getValue().equals("Type")) {
                actionType = value.getValue();
            } else if (name.getValue().equals("Class")) {
                result.setComponentClassName(value.getValue());
            } else if (name.getValue().equals("ToolTip")) {
                result.setTooltip(value.getValue());
            }
        }
        result.setActionType(actionType);

        return result;
    }

    public TestCaseStepRule[] createTestCaseStepRules(TestCaseStep[] steps, File ruleFile, File guiFile)
            throws TestRunnerException {
        try {
            List<TestCaseStepRule> result = new ArrayList<TestCaseStepRule>();
            Builder b = new Builder();
            Document r = b.build(ruleFile);
            Document gui = b.build(guiFile);
            Nodes rules = r.query("//Rule | //Termination | //Startup");
            for (int i = 0; i < rules.size(); ++i) {
                Node tmp = rules.get(i);
                Element ruleElement = null;
                if (tmp instanceof Element) {
                    ruleElement = (Element) tmp;
                } else {
                    throw new TestRunnerException("Invalid input file with different meanings for rules!");
                }
                TestCaseStepRule rule = createTestCaseStepRule(steps, ruleElement, gui);
                result.add(rule);
            }

            return result.toArray(new TestCaseStepRule[0]);
        } catch (Exception e) {
            throw new TestRunnerException("Cannot create the rule from xml element!", e);
        }
    }

    private TestCaseStepRule createTestCaseStepRule(TestCaseStep[] steps, Element ruleElement, Document guiStructure)
            throws TestRunnerException {
        if ("Startup".equals(ruleElement.getQualifiedName())) {
            StartupRule r = new StartupRule();

            Elements stepElements = ruleElement.getChildElements();
            List<TestCaseStep> stepList = createTestCaseStepsFromElements(stepElements, steps, guiStructure);
            r.setExecutions(stepList);

            return r;
        } else if ("Termination".equals(ruleElement.getQualifiedName())) {
            TerminationRule r = new TerminationRule();

            Elements stepElements = ruleElement.getChildElements();
            List<TestCaseStep> stepList = createTestCaseStepsFromElements(stepElements, steps, guiStructure);
            r.setExecutions(stepList);

            return r;
        } else {
            DefaultRule r = new DefaultRule();

            Element targetStepElement = ruleElement.getFirstChildElement("Target").getFirstChildElement("Event");
            TestCaseStep targetStep = findTestCaseStep(steps, targetStepElement, guiStructure);
            r.setTargetStep(targetStep);

            Element beforeElement = ruleElement.getFirstChildElement("Before");
            if (beforeElement != null) {
                Elements stepElements = beforeElement.getChildElements("Event");
                List<TestCaseStep> stepList = createTestCaseStepsFromElements(stepElements, steps, guiStructure);
                r.setBeforeExecutions(stepList);
            } else {
                r.setBeforeExecutions(null);
            }

            Element aroundElement = ruleElement.getFirstChildElement("Around");
            if (aroundElement != null) {
                Elements stepElements = aroundElement.getChildElements("Event");
                List<TestCaseStep> stepList = createTestCaseStepsFromElements(stepElements, steps, guiStructure);
                r.setAroundExecutions(stepList);
            } else {
                r.setAroundExecutions(null);
            }

            Element afterElement = ruleElement.getFirstChildElement("After");
            if (afterElement != null) {
                Elements stepElements = afterElement.getChildElements("Event");
                List<TestCaseStep> stepList = createTestCaseStepsFromElements(stepElements, steps, guiStructure);
                r.setAfterExecutions(stepList);
            } else {
                r.setAfterExecutions(null);
            }

            // Times this rule to be performed.
            String tmp = ruleElement.getAttributeValue("Times");
            if (tmp != null) {
                r.setTimes(Integer.valueOf(tmp));
            }

            // At which position of target step should this rule be carried out.
            tmp = ruleElement.getAttributeValue("Positions");
            if (tmp == null) {
                tmp = "";
                for (StepPosition p : StepPosition.values()) {
                    tmp = tmp + p.name() + ",";
                }
            }
            String[] ps = tmp.split(",");
            List<StepPosition> pos = new ArrayList<StepPosition>();
            for (int i = 0; i < ps.length; ++i) {
                pos.add(StepPosition.valueOf(ps[i].toUpperCase()));
            }
            r.setPositions(pos);

            return r;
        }
    }

    protected List<TestCaseStep> createTestCaseStepsFromElements(Elements stepElements, TestCaseStep[] steps,
            Document guiStructure) throws TestRunnerException {
        List<TestCaseStep> stepList = new ArrayList<TestCaseStep>();
        for (int i = 0; i < stepElements.size(); ++i) {
            Element el = stepElements.get(i);
            stepList.add(findTestCaseStep(steps, el, guiStructure));
        }

        return stepList;
    }

    private TestCaseStep findTestCaseStep(TestCaseStep[] steps, Element stepElement, Document guiStructure)
            throws TestRunnerException {
        Element windowElem = stepElement.getFirstChildElement("Window");
        WindowName windowName = new WindowName(windowElem.getValue());

        Element componentElem = stepElement.getFirstChildElement("Component");
        ComponentId componentId = new ComponentId(componentElem.getValue());

        Element actionElem = stepElement.getFirstChildElement("Action");
        String actionText = actionElem.getValue();
        Action action = new Action(actionText);

        for (int i = 0; i < steps.length; ++i) {
            TestCaseStep step = steps[i];
            if (windowName.equals(step.getWindowName()) && componentId.equals(step.getComponentId())
                    && action.equals(step.getAction())) {
                return step;
            }
        }

        String name = stepElement.getFirstChildElement("Component").getValue();
        String q = "//Attributes[Property/Name=\"Title\" and Property/Value=\"" + name + "\"]";
        Nodes attributes = guiStructure.query(q);
        if (attributes.size() < 1) {
            throw new TestRunnerException("Cannot find attributes for " + name + "!");
        }
        Node tmp = attributes.get(0);
        Element attributesElement = null;
        if (tmp instanceof Element) {
            attributesElement = (Element) tmp;
        } else {
            throw new TestRunnerException("Invalid input file with different meanings for GUI structure!");
        }
        TestCaseStep step = createTestCaseStep(stepElement, attributesElement);

        return step;
    }

    public Element toXML(TestCaseStep step) {
        Element el = new Element("Step");

        Element windowElem = new Element("Window");
        windowElem.appendChild(step.getWindowName().toString());
        el.appendChild(windowElem);

        Element componentElem = new Element("Component");
        componentElem.appendChild(step.getComponentId().toString());
        el.appendChild(componentElem);

        Element actionElem = new Element("Action");
        actionElem.appendChild(step.getAction().toString());
        el.appendChild(actionElem);

        Element attributes = new Element("Attributes");

        Element property = new Element("Property");
        Element name = new Element("Name");
        Element value = new Element("Value");

        Element typeElem = (Element) property.copy();
        Element typeName = (Element) name.copy();
        Element typeValue = (Element) value.copy();
        typeName.appendChild("Type");
        typeValue.appendChild(step.getActionType());
        typeElem.appendChild(typeName);
        typeElem.appendChild(typeValue);

        Element classElem = (Element) property.copy();
        Element className = (Element) name.copy();
        Element classValue = (Element) value.copy();
        className.appendChild("Class");
        classValue.appendChild(step.getComponentClassName());
        classElem.appendChild(className);
        classElem.appendChild(classValue);

        Element toolTipElem = (Element) property.copy();
        Element toolTipName = (Element) name.copy();
        Element toolTipValue = (Element) value.copy();
        toolTipName.appendChild("ToolTip");
        toolTipValue.appendChild(step.getTooltip());
        toolTipElem.appendChild(toolTipName);
        toolTipElem.appendChild(toolTipValue);

        attributes.appendChild(typeElem);
        attributes.appendChild(classElem);
        attributes.appendChild(toolTipElem);

        el.appendChild(attributes);

        return el;
    }
}
