JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	ADD.java \
	ALU.java \
	ALUControl.java \
	ButtonListener.java \
	Control.java \
	DataMemory.java \
	Instruction.java \
	InstructionMemory.java \
	PC.java \
	Processor.java \
	RedirectText.java \
	Registers.java \
	Simulator.java \
	UserInterface.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
