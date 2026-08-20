/*
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2026, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 *
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.solver.lcg;

import org.chocosolver.sat.MiniSat;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Settings;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.loop.learn.LazyClauseGeneration;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;

public class BicliqueFactorisationExample {

    @DataProvider
    public Object[][] tf() {
        return new Object[][]{{false}, {true}};
    }

    @Test(dataProvider = "tf")
    public void test1(boolean v2) {
        Settings.PARAM_LEARNING_MODE = Settings.LearningMode.EXTENDED_RESOLUTION;
        Settings.PARAM_BICLIQUE_FACTORISATION_ALLDIFFERENT = true;
        Model model = new Model("Biclique Factorisation Example",
                Settings.init().setLCG(true));
        MiniSat.V2 = v2;
        IntVar a = model.intVar("a", 1, 10);
//        IntVar a = model.intVar("a", 4, 5);
        IntVar b = model.intVar("b", 2, 10);
//        IntVar b = model.intVar("b", 2, 5);
        IntVar u = model.intVar("u", 3, 6);
        IntVar v = model.intVar("v", 3, 6);
        IntVar x = model.intVar("x", 1, 4);
        IntVar y = model.intVar("y", 1, 4);
        model.allDifferent(new IntVar[]{a, b, u, v, x, y}, "AC_TUNED").post();
        //BoolVar uEq3 = model.arithm(u, "=", 3).reify();
        BoolVar uEq3 = model.boolVar("uEq3");
        model.reifyXeqC(u, 3, uEq3);
//        BoolVar uEq4 = model.arithm(u, "=", 4).reify();
        BoolVar uEq4 = model.boolVar("uEq4");
        model.reifyXeqC(u, 4, uEq4);
//        BoolVar vEq3 = model.arithm(v, "=", 3).reify();
        BoolVar vEq3 = model.boolVar("vEq3");
        model.reifyXeqC(v, 3, vEq3);
//        BoolVar vEq4 = model.arithm(v, "=", 4).reify();
        BoolVar vEq4 = model.boolVar("vEq4");
        model.reifyXeqC(v, 4, vEq4);
        model.addClausesBoolOrArrayEqualTrue(
                new BoolVar[]{
                        uEq3,
                        uEq4,
                        vEq3,
                        vEq4
                }
        );
//        LazyClauseGeneration.VERBOSE = true;
//        MiniSat.DEBUG = 2;
        Solver solver = model.getSolver();
        solver.setSearch(Search.inputOrderLBSearch(a, b, u, v, x, y));
//        solver.limitSolution(7);
//        solver.showDecisions(()->""+solver.getNodeCount()+" --> "+ Arrays.toString(new IntVar[]{a,b,u,v,x,y}));
        while (solver.solve()) {
            System.out.printf(
                    "Sol#%d : a=%d b=%d u=%d v=%d x=%d y=%d%n",
                    0,//solver.getSolutionCount(),
                    a.getValue(),
                    b.getValue(),
                    u.getValue(),
                    v.getValue(),
                    x.getValue(),
                    y.getValue()
            );
        }
        Assert.assertEquals(solver.getSolutionCount(), 1480, "wrong number of solutions");
        Assert.assertEquals(solver.getNodeCount(), 2968, "wrong number of nodes");
    }
}
