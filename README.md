MetaChainer
-----------

Simple implementation of chain of responsibility design pattern using annotations


Features
--------

* Annotations to mark classes as chains and methods as chain steps

* Annotation to mark a method as a validator of chain step 

* Possibility to stop the chain when errors occur or a step is invalid (breakOnErrors, breakOnInvalid attributes)

* Marking a method as Ensure, ensure method run when the chain breaks on errors


Example
-------

Defining the chain:

    package my.package.chain;
    
    @ChainName("myChain")
    public class MyChain {
	
	    @ChainStep(order = 1, breakOnErrors = true)
	    public void addOneToParameterOne(ChainContext context) {
	    
		    Integer parameter1 = (Integer) context.get("myparameter1");

		    context.set("myparameter1", ++parameter1);
	    }
	
	    @ChainStep(order = 2, breakOnInvalid = true)
	    public void addPostfixToParameterTwo(ChainContext context) {
		
		    String parameter2 = (String) context.get("myparameter2");
		    String newString = parameter2 + "myPostfix";
		
		    context.set("myparameter2", newString);
	    }
	    
	    @StepValidator({"addPostfixToParameterTwo"})
		public boolean invalidMethodValidator(ChainContext context) {
			return true;
		}
	
		@ChainEnsure
		public void ensure(ChainContext context) {
			context.set("ensure", "Ensure method run always on errors!");
		}
	
    }

Invoking the chain:

    public void runMyChain() {
		
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = factory.create(MyChain.class);
		
		ChainContext context =
		chain.setParameter("myParameter1", 1)
			.setParameter("myParameter2", "myString")
			.start();
			
		// Do something with the context...
	}


To Do
-----

* Add todo tasks


Copyright
---------

Copyright (c) 2011 Jorge Luis Pérez (jolisper@gmail.com). See LICENSE for details.

