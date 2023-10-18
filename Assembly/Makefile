init: 
	mkdir build
Build:
	yasm -f elf64 -g dwarf2 -l projeto.lst projeto.asm && ld -g projeto.o -o projeto
	mv projeto.o projeto.lst build
clean: 
	rm build/projeto.o
	rm build/projeto.lst
	rm projeto
run:
	./projeto
debug:
	gdb ./projeto
