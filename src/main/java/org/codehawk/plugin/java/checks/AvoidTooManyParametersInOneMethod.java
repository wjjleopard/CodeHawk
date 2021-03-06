/*
 * CodeHawk plugin
 * Copyright (C) 2016-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.codehawk.plugin.java.checks;

import java.util.Collections;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(key = "AvoidTooManyParametersInOneMethod")
/**
 * To use subsctiption visitor, just extend the IssuableSubscriptionVisitor.
 */
public class AvoidTooManyParametersInOneMethod extends IssuableSubscriptionVisitor {

	@Override
	public List<Tree.Kind> nodesToVisit() {
		// Register to the kind of nodes you want to be called upon visit.
		return Collections.singletonList(Tree.Kind.CLASS);// check every class
	}

	@Override
	public void visitNode(Tree tree) {
		ClassTree classTree = (ClassTree) tree;
		List<Tree> treeList = classTree.members();
		for (Tree tempTree : treeList) {
			if (tempTree.is(Tree.Kind.METHOD) || tempTree.is(Tree.Kind.CONSTRUCTOR)) {// check every method(constructor) in this class
				MethodTree methodTree = (MethodTree) tempTree;
				List<VariableTree> list = methodTree.parameters();
				if (!list.isEmpty() && list.size() >= 10){// if this method has >= 10 parameters
					reportIssue(methodTree, "There are too many parameters in this method !");// show this smell
				}
			}
		}
	}
}
