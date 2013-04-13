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
package nl.han.ica.ap.purify.module.java.duplicatecode;

import org.antlr.v4.runtime.tree.ParseTree;

import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationConstantRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationMethodOrConstantRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationMethodRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationTypeBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationTypeDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationTypeElementDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationTypeElementRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.AnnotationsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ArgumentsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ArrayCreatorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ArrayInitializerContext;
import nl.han.ica.ap.purify.language.java.JavaParser.BlockContext;
import nl.han.ica.ap.purify.language.java.JavaParser.BlockStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.BooleanLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CatchClauseContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CatchesContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassBodyDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassCreatorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassOrInterfaceDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassOrInterfaceModifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassOrInterfaceModifiersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassOrInterfaceTypeContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CompilationUnitContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstantDeclaratorContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstantDeclaratorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstantDeclaratorsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstantExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstructorBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ConstructorDeclaratorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CreatedNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CreatorContext;
import nl.han.ica.ap.purify.language.java.JavaParser.DefaultValueContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ElementValueArrayInitializerContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ElementValueContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ElementValuePairContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ElementValuePairsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnhancedForControlContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumBodyDeclarationsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumConstantContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumConstantNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumConstantsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.EnumDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExplicitConstructorInvocationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExplicitGenericInvocationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionAdditionSubtractionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionArithmeticContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionArrayElementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionBitwiseANDContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionBitwiseORContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionBitwiseShiftContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionBitwiseXORContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionCastContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionCompoundAssignmentContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionEqualToNotEqualToContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionExplicitGenericInvocationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionGreaterLessThanOrEqualContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionIdentifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionInstanceofContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionLogicalANDContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionLogicalORContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionMethodExpressionListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionNewInnerClassInstanceContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionNewInstanceContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionNotContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionPrefixContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionPrimaryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionSuffixContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionSuperContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionSuperIdentifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionTernaryConditionalContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionThisContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FieldDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ForControlContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ForInitContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ForUpdateContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParametersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.GenericMethodOrConstructorDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.GenericMethodOrConstructorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ImportDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InnerCreatorContext;
import nl.han.ica.ap.purify.language.java.JavaParser.IntegerLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceBodyDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceGenericMethodDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceMemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceMethodDeclaratorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceMethodOrFieldDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.InterfaceMethodOrFieldRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LocalVariableDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LocalVariableDeclarationStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclaratorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ModifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ModifiersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.NonWildcardTypeArgumentsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.NormalClassDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.NormalInterfaceDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PackageDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PackageOrTypeNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ParExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PrimaryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PrimitiveTypeContext;
import nl.han.ica.ap.purify.language.java.JavaParser.QualifiedNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.QualifiedNameListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementAssertContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementBlockContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementBreakContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementContinueContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementDoContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementForContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIdentifierStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIfContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementReturnContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementStatementExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementSwitchContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementSynchronizedContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementThrowContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementTryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementWhileContext;
import nl.han.ica.ap.purify.language.java.JavaParser.SwitchBlockStatementGroupContext;
import nl.han.ica.ap.purify.language.java.JavaParser.SwitchBlockStatementGroupsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.SwitchLabelContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeArgumentContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeArgumentsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeBoundContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeNameContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeParameterContext;
import nl.han.ica.ap.purify.language.java.JavaParser.TypeParametersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableInitializerContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableModifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableModifiersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VoidInterfaceMethodDeclaratorRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VoidMethodDeclaratorRestContext;

/**
 * Get the hash id of a ParseTree. 
 * Note: look if there is a cleaner solution for this. Maybe adding a 
 * hashCode by generating the parser (ANTLR).
 * 
 * @author Arjan
 */
class HashCode {
	private HashCode() {
	}
	
	/**
	 * Get the hash code of a ParseTree.
	 * 
	 * @param c ParseTree node.
	 * @return hash code or -1 if unkown object.
	 */
	public static int getHashCode(ParseTree c) {
		if (c instanceof AnnotationConstantRestContext) {
			return 1;
		} else if (c instanceof AnnotationContext) {
			return 2;
		} else if (c instanceof AnnotationMethodOrConstantRestContext) {
			return 3;
		} else if (c instanceof AnnotationMethodRestContext) {
			return 4;
		} else if (c instanceof AnnotationNameContext) {
			return 5;
		} else if (c instanceof AnnotationTypeBodyContext) {
			return 6;
		} else if (c instanceof AnnotationTypeDeclarationContext) {
			return 7;
		} else if (c instanceof AnnotationTypeElementDeclarationContext) {
			return 8;
		} else if (c instanceof AnnotationTypeElementRestContext) {
			return 9;
		} else if (c instanceof AnnotationsContext) {
			return 10;
		} else if (c instanceof ArgumentsContext) {
			return 11;
		} else if (c instanceof ArrayCreatorRestContext) {
			return 12;
		} else if (c instanceof ArrayInitializerContext) {
			return 13;
		} else if (c instanceof BlockContext) {
			return 14;
		} else if (c instanceof BlockStatementContext) {
			return 15;
		} else if (c instanceof BooleanLiteralContext) {
			return 16;
		} else if (c instanceof CatchClauseContext) {
			return 17;
		} else if (c instanceof CatchesContext) {
			return 18;
		} else if (c instanceof ClassBodyContext) {
			return 19;
		} else if (c instanceof ClassBodyDeclarationContext) {
			return 20;
		} else if (c instanceof ClassCreatorRestContext) {
			return 21;
		} else if (c instanceof ClassDeclarationContext) {
			return 22;
		} else if (c instanceof ClassOrInterfaceDeclarationContext) {
			return 23;
		} else if (c instanceof ClassOrInterfaceModifierContext) {
			return 24;
		} else if (c instanceof ClassOrInterfaceModifiersContext) {
			return 25;
		} else if (c instanceof ClassOrInterfaceTypeContext) {
			return 26;
		} else if (c instanceof CompilationUnitContext) {
			return 27;
		} else if (c instanceof ConstantDeclaratorContext) {
			return 28;
		} else if (c instanceof ConstantDeclaratorRestContext) {
			return 29;
		} else if (c instanceof ConstantDeclaratorsRestContext) {
			return 30;
		} else if (c instanceof ConstantExpressionContext) {
			return 31;
		} else if (c instanceof ConstructorBodyContext) {
			return 32;
		} else if (c instanceof ConstructorDeclaratorRestContext) {
			return 33;
		} else if (c instanceof CreatedNameContext) {
			return 34;
		} else if (c instanceof CreatorContext) {
			return 35;
		} else if (c instanceof DefaultValueContext) {
			return 36;
		} else if (c instanceof ElementValueArrayInitializerContext) {
			return 37;
		} else if (c instanceof ElementValueContext) {
			return 38;
		} else if (c instanceof ElementValuePairContext) {
			return 39;
		} else if (c instanceof ElementValuePairsContext) {
			return 40;
		} else if (c instanceof EnhancedForControlContext) {
			return 41;
		} else if (c instanceof EnumBodyContext) {
			return 42;
		} else if (c instanceof EnumBodyDeclarationsContext) {
			return 43;
		} else if (c instanceof EnumConstantContext) {
			return 44;
		} else if (c instanceof EnumConstantNameContext) {
			return 45;
		} else if (c instanceof EnumConstantsContext) {
			return 46;
		} else if (c instanceof EnumDeclarationContext) {
			return 47;
		} else if (c instanceof ExplicitConstructorInvocationContext) {
			return 48;
		} else if (c instanceof ExplicitGenericInvocationContext) {
			return 49;
		} else if (c instanceof ExpressionAdditionSubtractionContext) {
			return 50;
		} else if (c instanceof ExpressionArithmeticContext) {
			return 51;
		} else if (c instanceof ExpressionArrayElementContext) {
			return 52;
		} else if (c instanceof ExpressionBitwiseANDContext) {
			return 53;
		} else if (c instanceof ExpressionBitwiseORContext) {
			return 54;
		} else if (c instanceof ExpressionBitwiseShiftContext) {
			return 55;
		} else if (c instanceof ExpressionBitwiseXORContext) {
			return 56;
		} else if (c instanceof ExpressionCastContext) {
			return 57;
		} else if (c instanceof ExpressionCompoundAssignmentContext) {
			return 58;
		} else if (c instanceof ExpressionEqualToNotEqualToContext) {
			return 59;
		} else if (c instanceof ExpressionExplicitGenericInvocationContext) {
			return 60;
		} else if (c instanceof ExpressionGreaterLessThanOrEqualContext) {
			return 61;
		} else if (c instanceof ExpressionIdentifierContext) {
			return 62;
		} else if (c instanceof ExpressionInstanceofContext) {
			return 63;
		} else if (c instanceof ExpressionListContext) {
			return 64;
		} else if (c instanceof ExpressionLogicalANDContext) {
			return 65;
		} else if (c instanceof ExpressionLogicalORContext) {
			return 66;
		} else if (c instanceof ExpressionMethodExpressionListContext) {
			return 67;
		} else if (c instanceof ExpressionNewInnerClassInstanceContext) {
			return 68;
		} else if (c instanceof ExpressionNewInstanceContext) {
			return 69;
		} else if (c instanceof ExpressionNotContext) {
			return 70;
		} else if (c instanceof ExpressionPrefixContext) {
			return 71;
		} else if (c instanceof ExpressionPrimaryContext) {
			return 72;
		} else if (c instanceof ExpressionSuffixContext) {
			return 73;
		} else if (c instanceof ExpressionSuperContext) {
			return 74;
		} else if (c instanceof ExpressionSuperIdentifierContext) {
			return 75;
		} else if (c instanceof ExpressionTernaryConditionalContext) {
			return 76;
		} else if (c instanceof ExpressionThisContext) {
			return 77;
		} else if (c instanceof FieldDeclarationContext) {
			return 78;
		} else if (c instanceof ForControlContext) {
			return 79;
		} else if (c instanceof ForInitContext) {
			return 80;
		} else if (c instanceof ForUpdateContext) {
			return 81;
		} else if (c instanceof FormalParameterContext) {
			return 82;
		} else if (c instanceof FormalParameterDeclsContext) {
			return 83;
		} else if (c instanceof FormalParameterDeclsRestContext) {
			return 84;
		} else if (c instanceof FormalParametersContext) {
			return 85;
		} else if (c instanceof GenericMethodOrConstructorDeclContext) {
			return 86;
		} else if (c instanceof GenericMethodOrConstructorRestContext) {
			return 87;
		} else if (c instanceof ImportDeclarationContext) {
			return 88;
		} else if (c instanceof InnerCreatorContext) {
			return 89;
		} else if (c instanceof IntegerLiteralContext) {
			return 90;
		} else if (c instanceof InterfaceBodyContext) {
			return 91;
		} else if (c instanceof InterfaceBodyDeclarationContext) {
			return 92;
		} else if (c instanceof InterfaceDeclarationContext) {
			return 93;
		} else if (c instanceof InterfaceGenericMethodDeclContext) {
			return 94;
		} else if (c instanceof InterfaceMemberDeclContext) {
			return 95;
		} else if (c instanceof InterfaceMethodDeclaratorRestContext) {
			return 96;
		} else if (c instanceof InterfaceMethodOrFieldDeclContext) {
			return 97;
		} else if (c instanceof InterfaceMethodOrFieldRestContext) {
			return 98;
		} else if (c instanceof LiteralContext) {
			return 99;
		} else if (c instanceof LocalVariableDeclarationContext) {
			return 100;
		} else if (c instanceof LocalVariableDeclarationStatementContext) {
			return 101;
		} else if (c instanceof MemberDeclContext) {
			return 102;
		} else if (c instanceof MemberDeclarationContext) {
			return 103;
		} else if (c instanceof MethodBodyContext) {
			return 104;
		} else if (c instanceof MethodDeclarationContext) {
			return 105;
		} else if (c instanceof MethodDeclaratorRestContext) {
			return 106;
		} else if (c instanceof ModifierContext) {
			return 107;
		} else if (c instanceof ModifiersContext) {
			return 108;
		} else if (c instanceof NonWildcardTypeArgumentsContext) {
			return 109;
		} else if (c instanceof NormalClassDeclarationContext) {
			return 110;
		} else if (c instanceof NormalInterfaceDeclarationContext) {
			return 111;
		} else if (c instanceof PackageDeclarationContext) {
			return 112;
		} else if (c instanceof PackageOrTypeNameContext) {
			return 113;
		} else if (c instanceof ParExpressionContext) {
			return 114;
		} else if (c instanceof PrimaryContext) {
			return 115;
		} else if (c instanceof PrimitiveTypeContext) {
			return 116;
		} else if (c instanceof QualifiedNameContext) {
			return 117;
		} else if (c instanceof QualifiedNameListContext) {
			return 118;
		} else if (c instanceof StatementAssertContext) {
			return 119;
		} else if (c instanceof StatementBlockContext) {
			return 120;
		} else if (c instanceof StatementBreakContext) {
			return 121;
		} else if (c instanceof StatementContinueContext) {
			return 122;
		} else if (c instanceof StatementDoContext) {
			return 123;
		} else if (c instanceof StatementExpressionContext) {
			return 124;
		} else if (c instanceof StatementForContext) {
			return 125;
		} else if (c instanceof StatementIdentifierStatementContext) {
			return 126;
		} else if (c instanceof StatementIfContext) {
			return 127;
		} else if (c instanceof StatementReturnContext) {
			return 128;
		} else if (c instanceof StatementStatementContext) {
			return 129;
		} else if (c instanceof StatementStatementExpressionContext) {
			return 130;
		} else if (c instanceof StatementSwitchContext) {
			return 131;
		} else if (c instanceof StatementSynchronizedContext) {
			return 132;
		} else if (c instanceof StatementThrowContext) {
			return 133;
		} else if (c instanceof StatementTryContext) {
			return 134;
		} else if (c instanceof StatementWhileContext) {
			return 135;
		} else if (c instanceof SwitchBlockStatementGroupContext) {
			return 136;
		} else if (c instanceof SwitchBlockStatementGroupsContext) {
			return 137;
		} else if (c instanceof SwitchLabelContext) {
			return 138;
		} else if (c instanceof TypeArgumentContext) {
			return 139;
		} else if (c instanceof TypeArgumentsContext) {
			return 140;
		} else if (c instanceof TypeBoundContext) {
			return 141;
		} else if (c instanceof TypeContext) {
			return 142;
		} else if (c instanceof TypeDeclarationContext) {
			return 143;
		} else if (c instanceof TypeListContext) {
			return 144;
		} else if (c instanceof TypeNameContext) {
			return 145;
		} else if (c instanceof TypeParameterContext) {
			return 146;
		} else if (c instanceof TypeParametersContext) {
			return 147;
		} else if (c instanceof VariableDeclaratorContext) {
			return 148;
		} else if (c instanceof VariableDeclaratorIdContext) {
			return 149;
		} else if (c instanceof VariableDeclaratorsContext) {
			return 150;
		} else if (c instanceof VariableInitializerContext) {
			return 151;
		} else if (c instanceof VariableModifierContext) {
			return 152;
		} else if (c instanceof VariableModifiersContext) {
			return 153;
		} else if (c instanceof VoidInterfaceMethodDeclaratorRestContext) {
			return 154;
		} else if (c instanceof VoidMethodDeclaratorRestContext) {
			return 155;
		}

		return -1;
	}
}