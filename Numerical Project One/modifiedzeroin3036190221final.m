function [root, info] = modifiedzeroin3036190221final(func, Int, params)
%  We need to find a solution to f(x) = 0 with the function
%  f on the interval [a,b] fa*fb<0  params.maxit is the maximum number of
%  iterations, Tolerance
%MAXIT = max number of iterations
%TOLERANCE = the tolerance for the solution
%FTOLERANCE is equal to the function tolerance
MAXIT = params.maxit;
TOLERANCE = params.root_tol;
FTOLERANCE = params.func_tol;
a = Int.a;
b = Int.b;
c = (a+b)/2;
x0 = a;
x1 = b;
x2 = c;
f0 = func(x0);
f1 = func(x1);
f2 = func(x2);
fa = f0;
fb = f1;
Iterations = 0;
Iterations_steps =0;
info = struct('flag',1);


%%HERE WE CHECK THE INITAL CONDITION

%%%HERE WE SET UP A WHILE LOOP WHICH ESSENTIALLY STOPS ONCE ONE OF THESE
%%%CONDITIONSBREAK
    while (Iterations <= MAXIT)
        Iterations = Iterations+1;
        x3 = ((f1*f2)/((f0-f1)*(f0-f2)))*x0 + ((f0*f2)/((f1-f0)*(f1-f2)))*x1 + ((f0*f1)/((f2-f0)*(f2-f1)))*x2;
        if(abs(func(x3))<FTOLERANCE)
            root = x3;
            info = struct('flag',0);
            break;
        end
        
        if (x3<a || x3>b)
            for i=0:2
                c = (a+b)/2;
                if abs(func(x3))<FTOLERANCE
                    root = x3;
                    info = struct('flag',0);
                    break;
                end
                
                if func(a)*func(c) > 0
                    a=c;
                else
                    b=c;
                end
            end
            x0 = a;
            x1=b;
            x2 = (a+b)/2;
            f0=func(x0);
            f1 = func(x1);
            f2 = func(x2);
            root = x3;
        else
            x0=x1;
            x1=x2;
            x2=x3;
            f0 = f1;
            f1 = f2;
            f2 = func(x3);
            root = x3;
            
        end
    end
        %%PERFORMING 2 ITERATIONS OF THE BISECTION METHOD IF X3 IS NOT IN
        %%THE INTERVAL AFTER THE INITIAL IQI
%         if (x3<a || x3>b) %%Pick th enumber of steps CHECK YOUR TWO SUBINTERVALS HERE BISECTION METHOD
%             
%             for i=0:2
%                 c = (a+b)/2;
%                 %%if abs(x2-x0)<TOLERANCE;break,end
%                 if func(c)*func(a)>0 
%                    a=c;
%                 else 
%                     b=c;
%                 end
%             end
%             x0=a;
%             x1=b;
%             x2= (a+b)/2;
            
        
%%% THIS IS ESSENTIALLY SKIPPING OVER THE LAST PART AND THIS MEANS THAT X3 IS WITHIN THE INITAL INTERVAL THEREFORE ITERATIONS INCREASE BY
%%% THEN WE SHIFT EACH VARIABLE AS STATED IN ALGORITHM, X0 (A) BECOMES
%%% X1(B) X1 WHICH WAS B BECOMES X2 WHICH IS C AND X2 BECOMES X3 WHICH THE
%%% ESTIMATION
        
%         else
%             x0 = x1;
%             x1 = x2;
%             x2 = x3;
%             f0=f1;
%             f1=f2;
%             f2=func(x3);
%             if(f2<FTOLERANCE)
%                 root = x3;
%                 break;
%             end
%         end
%         
%         Iterations_steps = Iterations_steps + 1;
        
%%HERE WE SETUP THE NEW INTERVALS
        %%if (f3*f0 < 0 && x0<x3)
            %%a_new = x0;
            %%b_new = x3;
        %%end
        %%if (f3*f1)<0 && (x1>x3)
            %%a_new = x3;
            %%b_new = x1;
        %%end
%%%SHOULD only do 1 IQI steps but if it hasnt decreased by a factor of 2
%%%then you do bisection // 2 bisection stpes // Keep track of what the
%%%value was a few iterations ago so you can compare
%%%IF AFTER 4 ITERATIONS THE ABSOLUTE VALEU OF THE NEW INTERVALS ISNT
%%%GREATER THAN BY THE OLD BY A FACTOR OF 2 WE BISECT AGAIN
    %%if ( Iterations_steps ==4 && abs(a_new-b_new)> abs(a-b)/2 || abs(f3)> abs(f2)/2)
%         for i=:2
%                 x3 = (a_new+b_new)/2;
%                 if abs(x3-a_new)<TOLERANCE;break,end
%                 if f3*(func(a_new))>0
%                     a_new = x3;
%                 else
%                     b_new = x3;
%                 end
%         end
%         Iterations_steps = 0;
%         f3 = func(x3);
%     end
    
    info.Iterations = Iterations;
    
end





%%% TEST FUNCTIONS THAT NEED TO PASS: 
%%%f(x) = x+1-2*sin(pi*x), root in [0, 0.5]
%%%f(x) = x+1-2*sin(pi*x), root in [0.5, 1]
%%%f(x) = x^3-x^2 + x/3-1/27, root in [0, 1]
%%%f(x) = x^2-1 - exp(1-x^2), root in [1, 2]
%%%f(x) = x^2-1 - exp(1-x^2), root in [-2, -1]
   



    




