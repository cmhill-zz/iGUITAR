
TEXFILES := $(wildcard *.tex)

all: main.pdf

main.pdf: $(TEXFILES)
	pdflatex main.tex

clean:
	rm main.pdf main.aux main.log
