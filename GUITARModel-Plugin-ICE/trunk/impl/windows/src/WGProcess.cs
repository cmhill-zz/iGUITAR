using System;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Collections.Generic;
using System.Windows.Automation;

namespace guitar.model {

    public class WGProcess : ProcessDisp_
    {
        private System.Diagnostics.Process process;

        public WGProcess(string path, string[] args)
        {
            ProcessStartInfo processStartInfo =
                new ProcessStartInfo(path);
            StringBuilder arguments = new StringBuilder();
            foreach (string s in args)
            {
                arguments.Append(s).Append(" ");
            }
            processStartInfo.Arguments = arguments.ToString();

            process = System.Diagnostics.Process.Start(processStartInfo);
        }

        public override int pid(Ice.Current current__)
        {
            return process.Id;
        }

        public override void disconnect(Ice.Current current__)
        {
            try
            {
                process.Kill();
            }
            catch (InvalidOperationException)
            {
            }
            process = null;

            /* destroy this servant from the adapter */
            current__.adapter.remove(current__.id);
        }

        public override Dictionary<int, WindowPrx> getAllWindow(Ice.Current current__)
        {
            if (process == null)
                return null;

            PropertyCondition processCondition = new PropertyCondition(
                AutomationElement.ProcessIdProperty, process.Id);
            PropertyCondition windowCondition = new PropertyCondition(
                AutomationElement.ControlTypeProperty, ControlType.Window);
            Condition condition = new AndCondition(processCondition, windowCondition);

            // Go through all windows with the above conditions
            AutomationElementCollection rootWindowCollection =
                AutomationElement.RootElement.FindAll(TreeScope.Children, condition);

            Dictionary<int, WindowPrx> windows = new Dictionary<int, WindowPrx>();
            for (int i = 0; i < rootWindowCollection.Count; i++)
            {
                windows.Add(i, new WGWindow(rootWindowCollection[i]).Proxy);
            }
            return windows;
        }
    }
}