// run as 
//   java generate_ant_test oracle test out.xml
//
//   oracle - the directory containing ground truth
//   test - the directory being evaluated
//   out.xml - the file created that does the verification
//
// then use the generated out.xml as:
//   ant -f out.xml

class generate_ant_test {

  /* Depth of the stack, for pretty-print. */
  private static int level = 0;

  /* Proper extension of an (output) test file. */
  private static final String ext = ".tst";

  private static void down_level() {
    level++;
  }

  private static void up_level() {
    level--;
  }

  /* Pretty print. */
  private static void pretty_print(java.io.PrintStream stream, String msg) {
    for(int q = 0; q < level; q++)
      stream.print("  ");
    stream.println(msg);
  }

  public static void main(String[] args) {
    java.io.FileOutputStream Output;
    java.io.PrintStream file;

    if(args.length != 3) {
      System.err.println("Usage: java generator_ant_test <oracle_directory> <test_directory> <output build file>");
      return;
    }
    try {
      Output = new java.io.FileOutputStream(args[2]);
      file = new java.io.PrintStream(Output);

      pretty_print(file, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      pretty_print(file, "<project name=\"test_diff\" default=\"diff_value\" basedir=\".\">"); down_level();
      pretty_print(file, "<path id=\"antcontrib.lib\">"); down_level();
      pretty_print(file, "<pathelement location=\"lib/ant-contrib.jar\" />");
      up_level(); pretty_print(file, "</path>"); 
      pretty_print(file, "<taskdef resource=\"net/sf/antcontrib/antcontrib.properties\" classpathref=\"antcontrib.lib\"/>");
      pretty_print(file, "<property name=\"ant.enable.asserts\" value=\"true\"/>");
      pretty_print(file, "<target name=\"diff_value\" description=\"test diff\">"); down_level();
      String oracle = args[0];
      String test = args[1];
      java.io.File odir = new java.io.File(args[0]);
      java.io.File tdir = new java.io.File(args[1]);

      if(!odir.exists())
        throw new RuntimeException("Oracle directory " + args[0] + " does not exist!");
      if(!tdir.exists())
        throw new RuntimeException("Testing directory " + args[1] + " does not exist!");

      String[] children = odir.list();
      String[] children2 = tdir.list();
      if (children == null) {
      } else {
        file.println();
        pretty_print(file," <!-- Check for number of files matching.-->");
        for (int i=0; i<children2.length; i++) {

          /* Apply checks only for test file, those ending with the proper extension. */
          if(children2[i].endsWith(ext)) {
            pretty_print(file, "<assert failonerror=\"true\" message=\"Extra file\">"); down_level();
            pretty_print(file, "<bool><available file=\""+oracle+"/"+children2[i]+"\" /></bool>");
            up_level(); pretty_print(file, "</assert>");
          }
        }

        file.println();
        pretty_print(file," <!-- Check for matching.-->");
        for (int i=0; i<children.length; i++) {
          /* Apply checks only for test file, those ending with the proper extension. */
          if(children[i].endsWith(ext)) {
            pretty_print(file, "<assert failonerror=\"true\" message=\"No match\">"); down_level();
            pretty_print(file, "<bool><filesmatch file1=\""+oracle+"/"+children[i]+"\" file2=\""+test+"/"+children[i]+"\"/></bool>");
            up_level(); pretty_print(file, "</assert>");
          }
        }
      }

      pretty_print(file, "<echo message=\"Test passed, output tests for ${input} input using ${plugin} plugin match corresponding ground truth!\" />");

      up_level(); pretty_print(file, "</target>");
      up_level(); pretty_print(file, "</project>");
    } catch(Exception e) {
      //System.out.println("Error.");
      e.printStackTrace();
      System.exit(1);
    }
  }

}
