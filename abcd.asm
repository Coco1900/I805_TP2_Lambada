DATA SEGMENT
	a DD
	b DD
	aux DD
	a DD
	b DD
DATA ENDS
CODE SEGMENT
	in eax
	mov a, eax
	in eax
	mov b, eax
debut_while_1:
	mov eax, 0
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle faux_gt_2
	mov eax, 1
	jmp sortie_gt_2
faux_gt_2 :
	mov eax, 0
sortie_gt_2 :
	jz sortie_while_2
	mov eax, b
	push eax
	mov eax, a
	pop ebx
	mov ecx, eax
	div ecx, ebx
	mul ecx, ebx
	sub eax, ecx
	mov aux, eax
	mov eax, b
	mov a, eax
	mov eax, aux
	mov b, eax
	jmp debut_while_2
sortie_while_2:
	mov eax, a
	out eax
CODE ENDS