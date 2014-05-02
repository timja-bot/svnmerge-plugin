//  Show the last integration status
package jenkins.plugins.svnmerge.IntegratableProjectAction

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import org.apache.commons.lang.StringUtils;

import static jenkins.plugins.svnmerge.RepositoryLayoutEnum.CUSTOM;

def f = namespace(lib.FormTagLib.class)
def l = namespace(lib.LayoutTagLib.class)
def t = namespace(lib.JenkinsTagLib.class)


l.layout(norefresh:"true",title:_("title",my.project.displayName)) {
    include(my.project, "sidepanel")
    l.main_panel {
        h1 {
            img (src:"${rootURL}/plugin/svnmerge/48x48/sync.gif")
            text(_("Feature Branches"))
        }

        raw("<p>This project tracks integrations from branches via <tt>svn merge</tt></p>")
		
		def repoLayout = my.repositoryLayout
		p(_("Repository URL: "+repoLayout.scmModuleLocation))
		p(_("Detected repository layout: "+repoLayout.layout))
		if (StringUtils.isNotEmpty(repoLayout.subProjectName)) {
			p(_("Detected subproject name: "+repoLayout.subProjectName))
		}
		if (repoLayout.layout!=CUSTOM) {
			p(_("Default new branch location: "+repoLayout.defaultNewBranchUrl))
			p(_("Default new tag location: "+repoLayout.defaultNewDevTagUrl))
		} else {
			p(_("Unable to detect default branch or tag locations for custom layout repositories"))
		}

        def branches = my.branches;
        if (branches.size()>0) {
            h2(_("Existing Feature Branches"))
            ul(style:"list-style:none") {
                branches.each { b ->
                    li {
                        t.jobLink(job:b)
                    }
                }
            }
        }
		
        h2(_("Create a new branch"))
        p(_("createBranchBlurb"))
        p {
            form (name:"new", method:"post", action:"newBranch") {
				
				raw("<table>")
				
				raw("<tr>")
				raw("<td>")
				text(_("Branch Name")+":")
				raw("</td>")
				raw("<td>")
				input (type:"text", name:"name", width:"30")
				raw("</td>")
				raw("</tr>")
				
				raw("<tr>")
				raw("<td>")
				text(_("Commit Message")+":")
				raw("</td>")
				raw("<td>")
				input (type:"text", name:"commitMessage")
				raw("</td>")
				raw("</tr>")
				
				raw("<tr>")
				raw("<td>")
				text(_("Override default branch location")+":")
				raw("</td>")
				raw("<td>")
				input (type:"text", name:"newLocation", width:"90")
				raw("</td>")
				raw("</tr>")
				
				raw("<tr>")
				raw("<td>")
				text(_("Create a development tag")+":")
				raw("</td>")
				raw("<td>")
				input (type:"checkbox", name:"tag", width:"30")
				raw("</td>")
				raw("</tr>")
				
				raw("<tr>")
				raw("<td>")
				text(_("Override default tag location")+":")
				raw("</td>")
				raw("<td>")
				input (type:"text", name:"tagName")
				raw("</td>")
				raw("</tr>")
				
				raw("</table>")
				
				input (type:"hidden", name:"defaultNewBranchUrl", value:repoLayout.defaultNewBranchUrl)
				input (type:"hidden", name:"defaultNewDevTagUrl", value:repoLayout.defaultNewDevTagUrl)
				
                f.submit(value:_("Create"))
            }
        }
		
		
    }
}
