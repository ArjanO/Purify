/**
 * Copyright (c) 2013 HAN University of Applied Sciences
 * Arjan Oortgiese
 * Boyd Hofman
 * Joëll Portier
 * Michiel Westerbeek
 * Tim Waalewijn
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package nl.han.ica.ap.purify.modles;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Store information about files.
 * 
 * @author Arjan
 */
public class SourceFile {
	private String path;
	
	private CommonTokenStream tokens;
	private ParseTree parseTree;
	
	private List<IIssue> issues;
	
	/**
	 * Create a new file.
	 * 
	 * @param path Path to the file.
	 * @param tokens Token stream.
	 * @param tree Parse tree.
	 */
	public SourceFile(String path, CommonTokenStream tokens, ParseTree tree) {
		this.path = path;
		this.tokens = tokens;
		this.parseTree = tree;
		
		this.issues = new ArrayList<IIssue>();
	}
	
	/**
	 * Get the file location on the file system.
	 * 
	 * @return Location of the file.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Get the common token stream of the file.
	 * 
	 * @return The common token stream.
	 */
	public CommonTokenStream getTokenStream() {
		return tokens;
	}
	
	/**
	 * Get the parse tree of the file. 
	 * 
	 * @return Parse tree
	 */
	public ParseTree getParseTree() {
		return parseTree;
	}
	
	/**
	 * Get the issue at index.
	 * 
	 * @param index Index.
	 * @return Issue
	 */
	public IIssue getIssue(int index) {
		return issues.get(index);
	}
	
	/**
	 * Add a new issue.
	 * 
	 * @param issue
	 */
	public void addIssue(IIssue issue) {
		issues.add(issue);
	}
	
	/**
	 * Get the number of issues.
	 * 
	 * @return Number of issues.
	 */
	public int getIssuesSize() {
		return issues.size();
	}
}
