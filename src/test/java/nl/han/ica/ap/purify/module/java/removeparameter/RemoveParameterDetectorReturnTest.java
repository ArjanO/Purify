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
package nl.han.ica.ap.purify.module.java.removeparameter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.After;
import org.junit.Before;

/**
 * Unit test for {@link RemoveParameterDetector}
 * This unit test only test only method with return type.
 * 
 * @author Arjan
 */
public class RemoveParameterDetectorReturnTest
	extends RemoveParameterDetectorBaseTest {		
		private MemberDeclarationContext memberDeclCtx;
		private MethodDeclarationContext methodDeclCtx;
		private TerminalNode identifier;
		
		@Before
		public void setUp() {
			detector = new RemoveParameterDetectorListener();
			
			ctx = createMock(MemberDeclContext.class);
			memberDeclCtx = createMock(MemberDeclarationContext.class);
			methodDeclCtx = createMock(MethodDeclarationContext.class);
			identifier = createMock(TerminalNode.class);
			
			expect(identifier.getText()).andReturn("test").anyTimes();
			
			expect(ctx.voidMethodDeclaratorRest()).andReturn(null).anyTimes();
			expect(ctx.memberDeclaration()).andReturn(
					memberDeclCtx).anyTimes();
			expect(memberDeclCtx.methodDeclaration()).andReturn(
					methodDeclCtx).anyTimes();
			
			expect(methodDeclCtx.Identifier()).andReturn(
					identifier).anyTimes();
			
			replay(ctx);
			replay(memberDeclCtx);
			replay(methodDeclCtx);
		}
		
		@After
		public void shutdown() {
			verify(ctx);
			verify(memberDeclCtx);
			verify(methodDeclCtx);
		}
}
